package ulitsa.raskolnikova.connections.mapper.provider.impl;

import org.mapstruct.*;
import ulitsa.raskolnikova.connections.dto.provider.impl.OAuth2ProviderDto;
import ulitsa.raskolnikova.connections.entity.provider.OAuth2Provider;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OAuth2ProviderMapper {
    @Mapping(target = "id", ignore = true)
    OAuth2Provider toEntity(OAuth2ProviderDto dto);

    OAuth2ProviderDto toDto(OAuth2Provider entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(OAuth2ProviderDto dto, @MappingTarget OAuth2Provider entity);
}
