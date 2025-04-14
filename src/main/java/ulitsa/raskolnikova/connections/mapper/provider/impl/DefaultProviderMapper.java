package ulitsa.raskolnikova.connections.mapper.provider.impl;

import org.mapstruct.*;
import ulitsa.raskolnikova.connections.dto.provider.ProviderDto;
import ulitsa.raskolnikova.connections.entity.provider.Provider;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DefaultProviderMapper {
    @Mapping(target = "id", ignore = true)
    Provider toEntity(ProviderDto dto);

    ProviderDto toDto(Provider entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(ProviderDto dto, @MappingTarget Provider entity);
}
