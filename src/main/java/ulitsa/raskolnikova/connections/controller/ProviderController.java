package ulitsa.raskolnikova.connections.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ulitsa.raskolnikova.connections.dto.provider.ProviderDto;
import ulitsa.raskolnikova.connections.service.ProviderService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/providers")
public class ProviderController {
    private final ProviderService providerService;

    @PostMapping
    public ProviderDto createProvider(@RequestBody ProviderDto provider) {
        return providerService.createProvider(provider);
    }

    @PutMapping("/{providerId}")
    public ProviderDto updateProvider(@PathVariable UUID providerId, @RequestBody ProviderDto provider) {
        provider.setId(providerId);
        return providerService.updateProvider(provider);
    }

    @GetMapping("/{providerId}")
    public ProviderDto getProvider(@PathVariable UUID providerId) {
        return providerService.getProvider(providerId);
    }

    @GetMapping
    public List<ProviderDto> getProviders() {
        return providerService.getProviders();
    }

    @DeleteMapping("/{providerId}")
    public void deleteProvider(@PathVariable UUID providerId) {
        providerService.deleteProvider(providerId);
    }
}
