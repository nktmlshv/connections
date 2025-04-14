package ulitsa.raskolnikova.connections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ulitsa.raskolnikova.connections.dto.provider.ProviderDto;
import ulitsa.raskolnikova.connections.entity.provider.Provider;
import ulitsa.raskolnikova.connections.mapper.provider.ProviderMapper;
import ulitsa.raskolnikova.connections.repository.ProviderRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProviderService {
    private final ProviderRepository providerRepository;
    private final ProviderMapper providerMapper;

    public ProviderDto createProvider(ProviderDto providerDto) {
        return providerMapper.toDto(providerRepository.save(providerMapper.toEntity(providerDto)));
    }

    public ProviderDto updateProvider(ProviderDto providerDto) {
        Provider provider = getProviderEntity(providerDto.getId());
        providerMapper.updateEntity(providerDto, provider);
        return providerMapper.toDto(providerRepository.save(provider));
    }

    public ProviderDto getProvider(UUID providerId) {
        return providerMapper.toDto(getProviderEntity(providerId));
    }

    public void deleteProvider(UUID providerId) {
        providerRepository.deleteById(providerId);
    }

    public List<ProviderDto> getProviders() {
        return providerRepository
                .findAll()
                .stream()
                .map(providerMapper::toDto)
                .collect(Collectors.toList());
    }

    private Provider getProviderEntity(UUID providerId) {
        return providerRepository
                .findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider with id " + providerId + " not found"));
    }
}
