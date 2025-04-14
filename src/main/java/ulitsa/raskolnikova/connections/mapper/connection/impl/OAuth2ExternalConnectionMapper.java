package ulitsa.raskolnikova.connections.mapper.connection.impl;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ulitsa.raskolnikova.connections.dto.connection.impl.OAuth2ExternalConnectionDto;
import ulitsa.raskolnikova.connections.dto.connection.util.OAuth2TokenResponse;
import ulitsa.raskolnikova.connections.entity.connection.impl.OAuth2ExternalConnection;
import ulitsa.raskolnikova.connections.repository.ProviderRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class OAuth2ExternalConnectionMapper {
    @Autowired
    private ProviderRepository providerRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "providerId", target = "provider.id")
    @Mapping(target = "scopes", ignore = true)
    @Mapping(target = "connected", defaultValue = "false")
    public abstract OAuth2ExternalConnection toEntity(OAuth2ExternalConnectionDto dto);

    @Mapping(
            target = "expiresAt",
            expression = "java(java.time.Instant.now().plusSeconds(dto.expiresIn() - 120))"
    )
    public abstract OAuth2ExternalConnection toEntity(OAuth2TokenResponse dto);

    @Mapping(source = "provider.id", target = "providerId")
    public abstract OAuth2ExternalConnectionDto toDto(OAuth2ExternalConnection connection);

    @AfterMapping
    protected void mergeScopes(OAuth2TokenResponse dto, @MappingTarget OAuth2ExternalConnection entity) {
        if (entity.getScopes() == null) {
            entity.setScopes(new HashSet<>());
        }

        if (dto.scope() != null) {
            entity.getScopes().addAll(Arrays.stream(dto.scope().split(" ")).collect(Collectors.toSet()));
        }
    }

    @AfterMapping
    protected void mergeScopes(OAuth2ExternalConnectionDto dto, @MappingTarget OAuth2ExternalConnection entity) {
        if (entity.getScopes() == null) {
            entity.setScopes(new HashSet<>());
        }

        if (dto.getScopes() != null) {
            entity.getScopes().addAll(dto.getScopes());
        }
    }

    @Mapping(target = "scopes", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntity(OAuth2ExternalConnectionDto dto, @MappingTarget OAuth2ExternalConnection entity);

    @Mapping(target = "scopes", ignore = true)
    @Mapping(
            target = "expiresAt",
            expression = "java(java.time.Instant.now().plusSeconds(dto.expiresIn() - 120))"
    )
    public abstract void updateEntity(OAuth2TokenResponse dto, @MappingTarget OAuth2ExternalConnection entity);
}
