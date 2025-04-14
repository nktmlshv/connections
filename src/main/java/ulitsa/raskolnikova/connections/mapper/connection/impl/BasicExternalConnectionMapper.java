package ulitsa.raskolnikova.connections.mapper.connection.impl;

import org.mapstruct.*;
import ulitsa.raskolnikova.connections.dto.connection.impl.BasicExternalConnectionDto;
import ulitsa.raskolnikova.connections.entity.connection.impl.BasicExternalConnection;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BasicExternalConnectionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "providerId", target = "provider.id")
    @Mapping(target = "connected", defaultValue = "false")
    BasicExternalConnection toEntity(BasicExternalConnectionDto dto);

    @Mapping(source = "provider.id", target = "providerId")
    BasicExternalConnectionDto toDto(BasicExternalConnection connection);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(BasicExternalConnectionDto dto, @MappingTarget BasicExternalConnection entity);
}
