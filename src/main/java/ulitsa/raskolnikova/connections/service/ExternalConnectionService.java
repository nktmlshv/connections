package ulitsa.raskolnikova.connections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ulitsa.raskolnikova.connections.client.OAuth2Client;
import ulitsa.raskolnikova.connections.dto.connection.ExternalConnectionDto;
import ulitsa.raskolnikova.connections.dto.connection.impl.OAuth2ExternalConnectionDto;
import ulitsa.raskolnikova.connections.dto.connection.util.ExternalConnectionResponse;
import ulitsa.raskolnikova.connections.dto.connection.util.OAuth2TokenResponse;
import ulitsa.raskolnikova.connections.dto.connection.util.OAuth2Url;
import ulitsa.raskolnikova.connections.entity.connection.ExternalConnection;
import ulitsa.raskolnikova.connections.entity.connection.impl.OAuth2ExternalConnection;
import ulitsa.raskolnikova.connections.exception.ConnectionExistsException;
import ulitsa.raskolnikova.connections.mapper.connection.ExternalConnectionMapper;
import ulitsa.raskolnikova.connections.repository.ExternalConnectionRepository;
import ulitsa.raskolnikova.connections.repository.ProviderRepository;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExternalConnectionService {
    private final ExternalConnectionRepository externalConnectionRepository;
    private final OAuth2Client oAuth2Client;
    private final ExternalConnectionMapper externalConnectionMapper;
    private final ProviderRepository providerRepository;

    public ExternalConnectionResponse createExternalConnection(ExternalConnectionDto connectionDto) {
        if (externalConnectionRepository
                .existsByUserIdAndProviderId(connectionDto.getUserId(), connectionDto.getProviderId())) {
            throw new ConnectionExistsException("User already exists with provider id "
                    + connectionDto.getProviderId());
        }

        ExternalConnection entity = externalConnectionMapper.toEntity(connectionDto);

        if (entity instanceof OAuth2ExternalConnection oAuth2Connection) {
            oAuth2Connection.setConnected(false);
            externalConnectionRepository.save(oAuth2Connection);
            OAuth2ExternalConnectionDto oAuth2ConnectionDto = (OAuth2ExternalConnectionDto) connectionDto;
            oAuth2Connection.setProvider(providerRepository
                    .findById(connectionDto.getProviderId())
                    .orElseThrow(() ->
                            new NoSuchElementException("No provider found for " + connectionDto.getProviderId())));
            return new OAuth2Url(oAuth2Client.buildAccessCodeUri(oAuth2Connection,
                    oAuth2ConnectionDto.getScopes()));
        }

        return externalConnectionMapper.toDto(externalConnectionRepository.save(entity));
    }

    public ExternalConnection createOAuth2ExternalConnection(UUID connectionId, String accessCode)
            throws IOException, InterruptedException {
        OAuth2ExternalConnection connection = (OAuth2ExternalConnection) externalConnectionRepository
                .findById(connectionId)
                .orElseThrow(() -> new NoSuchElementException("No such connection"));

        OAuth2TokenResponse tokenResponse = oAuth2Client.generateAccessToken(connection, accessCode);

        externalConnectionMapper.updateEntity(tokenResponse, connection);
        connection.setConnected(true);
        externalConnectionRepository.save(connection);

        return connection;
    }

    public ExternalConnectionDto getExternalConnection(UUID connectionId) throws IOException, InterruptedException {
        ExternalConnection entity = externalConnectionRepository.findById(connectionId)
                .orElseThrow(() -> new NoSuchElementException("No such external connection"));
        if (entity instanceof OAuth2ExternalConnection oAuth2ExternalConnection) {
            return externalConnectionMapper.toDto(updateAccessToken(oAuth2ExternalConnection));
        }
        return externalConnectionMapper.toDto(entity);
    }

    public ExternalConnection updateAccessToken(OAuth2ExternalConnection connection)
            throws IOException, InterruptedException {
        if (Instant.now().isBefore(connection.getExpiresAt())) {
            return connection;
        }
        OAuth2TokenResponse tokenResponse = oAuth2Client.refreshAccessToken(connection);
        externalConnectionMapper.updateEntity(tokenResponse, connection);
        return externalConnectionRepository.save(connection);
    }

    public ExternalConnectionResponse updateExternalConnection(ExternalConnectionDto connectionDto) {
        ExternalConnection connection = externalConnectionRepository.findById(connectionDto.getId())
                .orElseThrow(() -> new NoSuchElementException("No such external connection"));
        if (connectionDto instanceof OAuth2ExternalConnectionDto oAuth2Dto && oAuth2Dto.getScopes() != null) {
            Set<String> neededScopes = new HashSet<>(oAuth2Dto.getScopes());
            OAuth2ExternalConnection oAuth2Connection = (OAuth2ExternalConnection) connection;
            neededScopes.removeAll(oAuth2Connection.getScopes());
            oAuth2Dto.setScopes(null);
            externalConnectionMapper.updateEntity(oAuth2Dto, oAuth2Connection);
            ExternalConnection entity = externalConnectionRepository.save(connection);
            if (!neededScopes.isEmpty()) {
                return new OAuth2Url(oAuth2Client.buildAccessCodeUri(oAuth2Connection, neededScopes));
            }
            return externalConnectionMapper.toDto(entity);
        }
        externalConnectionMapper.updateEntity(connectionDto, connection);
        return externalConnectionMapper.toDto(externalConnectionRepository.save(connection));
    }

    public void deleteExternalConnection(UUID connectionId) {
        externalConnectionRepository.deleteById(connectionId);
    }

    public List<ExternalConnectionDto> getUserExternalConnections(UUID userId) {
        return externalConnectionRepository.findAllByUserId(userId).stream()
                .map(externalConnectionMapper::toDto)
                .collect(Collectors.toList());
    }

}
