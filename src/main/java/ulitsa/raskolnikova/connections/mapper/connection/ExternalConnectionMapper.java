package ulitsa.raskolnikova.connections.mapper.connection;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ulitsa.raskolnikova.connections.dto.connection.ExternalConnectionDto;
import ulitsa.raskolnikova.connections.dto.connection.impl.ApiKeyExternalConnectionDto;
import ulitsa.raskolnikova.connections.dto.connection.impl.BasicExternalConnectionDto;
import ulitsa.raskolnikova.connections.dto.connection.impl.OAuth2ExternalConnectionDto;
import ulitsa.raskolnikova.connections.dto.connection.util.OAuth2TokenResponse;
import ulitsa.raskolnikova.connections.entity.connection.ExternalConnection;
import ulitsa.raskolnikova.connections.entity.connection.impl.ApiKeyExternalConnection;
import ulitsa.raskolnikova.connections.entity.connection.impl.BasicExternalConnection;
import ulitsa.raskolnikova.connections.entity.connection.impl.OAuth2ExternalConnection;
import ulitsa.raskolnikova.connections.mapper.connection.impl.ApiKeyExternalConnectionMapper;
import ulitsa.raskolnikova.connections.mapper.connection.impl.BasicExternalConnectionMapper;
import ulitsa.raskolnikova.connections.mapper.connection.impl.OAuth2ExternalConnectionMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {OAuth2ExternalConnectionMapper.class,
                ApiKeyExternalConnectionMapper.class,
                BasicExternalConnectionMapper.class})
public abstract class ExternalConnectionMapper {
    @Autowired
    private ApiKeyExternalConnectionMapper apiKeyExternalConnectionMapper;
    @Autowired
    private BasicExternalConnectionMapper basicExternalConnectionMapper;
    @Autowired
    private OAuth2ExternalConnectionMapper oAuth2ExternalConnectionMapper;

    public ExternalConnection toEntity(ExternalConnectionDto dto) {
        if (dto instanceof ApiKeyExternalConnectionDto apiKeyDto) {
            return apiKeyExternalConnectionMapper.toEntity(apiKeyDto);
        } else if (dto instanceof BasicExternalConnectionDto basicDto) {
            return basicExternalConnectionMapper.toEntity(basicDto);
        } else if (dto instanceof OAuth2ExternalConnectionDto oAuth2Dto) {
            return oAuth2ExternalConnectionMapper.toEntity(oAuth2Dto);
        }
        return null;
    }

    public ExternalConnectionDto toDto(ExternalConnection entity) {
        if (entity instanceof ApiKeyExternalConnection apiKeyEntity) {
            return apiKeyExternalConnectionMapper.toDto(apiKeyEntity);
        } else if (entity instanceof BasicExternalConnection basicEntity) {
            return basicExternalConnectionMapper.toDto(basicEntity);
        } else if (entity instanceof OAuth2ExternalConnection oAuth2Entity) {
            return oAuth2ExternalConnectionMapper.toDto(oAuth2Entity);
        }
        return null;
    }

    public OAuth2ExternalConnection toEntity(OAuth2TokenResponse dto) {
        return oAuth2ExternalConnectionMapper.toEntity(dto);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateEntity(ExternalConnectionDto dto, @MappingTarget ExternalConnection entity) {
        if (dto instanceof ApiKeyExternalConnectionDto apiKeyDto) {
            apiKeyExternalConnectionMapper.updateEntity(apiKeyDto, (ApiKeyExternalConnection) entity);
        } else if (dto instanceof BasicExternalConnectionDto basicDto) {
            basicExternalConnectionMapper.updateEntity(basicDto, (BasicExternalConnection) entity);
        } else if (dto instanceof OAuth2ExternalConnectionDto oAuth2Dto) {
            oAuth2ExternalConnectionMapper.updateEntity(oAuth2Dto, (OAuth2ExternalConnection) entity);
        }
    }

    public void updateEntity(OAuth2TokenResponse dto, @MappingTarget OAuth2ExternalConnection entity) {
        oAuth2ExternalConnectionMapper.updateEntity(dto, entity);
    }
}