package ulitsa.raskolnikova.connections.mapper.provider;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import ulitsa.raskolnikova.connections.dto.provider.ProviderDto;
import ulitsa.raskolnikova.connections.dto.provider.impl.OAuth2ProviderDto;
import ulitsa.raskolnikova.connections.entity.provider.OAuth2Provider;
import ulitsa.raskolnikova.connections.entity.provider.Provider;
import ulitsa.raskolnikova.connections.mapper.provider.impl.DefaultProviderMapper;
import ulitsa.raskolnikova.connections.mapper.provider.impl.OAuth2ProviderMapper;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ProviderMapper {
    @Autowired
    private OAuth2ProviderMapper oAuth2ProviderMapper;
    @Autowired
    private DefaultProviderMapper defaultProviderMapper;

    public Provider toEntity(ProviderDto dto) {
        if (dto instanceof OAuth2ProviderDto oAuth2Dto) {
            return oAuth2ProviderMapper.toEntity(oAuth2Dto);
        }
        return defaultProviderMapper.toEntity(dto);
    }

    public ProviderDto toDto(Provider entity) {
        if (entity instanceof OAuth2Provider oAuth2Entity) {
            return oAuth2ProviderMapper.toDto(oAuth2Entity);
        }
        return defaultProviderMapper.toDto(entity);
    }

    public void updateEntity(ProviderDto dto, Provider entity) {
        if (entity instanceof OAuth2Provider oAuth2Entity) {
            oAuth2ProviderMapper.updateEntity((OAuth2ProviderDto) dto, oAuth2Entity);
        } else {
            defaultProviderMapper.updateEntity(dto, entity);
        }
    }
}
