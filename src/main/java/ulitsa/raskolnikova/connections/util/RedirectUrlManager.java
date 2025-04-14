package ulitsa.raskolnikova.connections.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class RedirectUrlManager {
    private final Map<UUID, String> redirectUrls = new HashMap<>();

    @Value("${oauth2.frontend.redirect-uri}")
    private String defaultRedirectUri;

    public String getRedirectUrl(UUID userId) {
        String redirectUrl = redirectUrls.getOrDefault(userId, defaultRedirectUri);
        redirectUrls.remove(userId);
        return redirectUrl;
    }

    public String saveRedirectUrl(UUID userId, String redirectUrl) {
        redirectUrls.put(userId, redirectUrl);
        return redirectUrl;
    }
}
