package ulitsa.raskolnikova.connections.mapper.connection.impl;

import org.mapstruct.*;
import ulitsa.raskolnikova.connections.dto.connection.impl.ApiKeyExternalConnectionDto;
import ulitsa.raskolnikova.connections.entity.connection.impl.ApiKeyExternalConnection;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiKeyExternalConnectionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "providerId", target = "provider.id")
    @Mapping(target = "connected", defaultValue = "false")
    ApiKeyExternalConnection toEntity(ApiKeyExternalConnectionDto dto);

    @Mapping(source = "provider.id", target = "providerId")
    ApiKeyExternalConnectionDto toDto(ApiKeyExternalConnection connection);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(ApiKeyExternalConnectionDto dto, @MappingTarget ApiKeyExternalConnection entity);
}
