package ulitsa.raskolnikova.connections.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import org.yaml.snakeyaml.external.com.google.gdata.util.common.base.PercentEscaper;
import ulitsa.raskolnikova.connections.dto.connection.util.OAuth2TokenResponse;
import ulitsa.raskolnikova.connections.entity.connection.impl.OAuth2ExternalConnection;
import ulitsa.raskolnikova.connections.entity.provider.OAuth2Provider;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;

@Component
public class OAuth2Client {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final PercentEscaper escaper = new PercentEscaper("/.", false);


    @Value("${oauth2.redirect-uri}")
    private String redirectUri;

    public OAuth2TokenResponse generateAccessToken(OAuth2ExternalConnection connection, String accessCode)
            throws IOException, InterruptedException {
        return getToken(
                buildAccessTokenUri(
                        (OAuth2Provider) Hibernate.unproxy(connection.getProvider()),
                        accessCode
                )
        );
    }

    public OAuth2TokenResponse refreshAccessToken(OAuth2ExternalConnection connection)
            throws IOException, InterruptedException {
        return getToken(buildRefreshTokenUri(connection));
    }

    public OAuth2TokenResponse getToken(String uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(uri))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> json = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(json.body(), OAuth2TokenResponse.class);
    }

    public String buildAccessCodeUri(OAuth2ExternalConnection connection, Set<String> addedScopes) {
        OAuth2Provider provider = (OAuth2Provider) Hibernate.unproxy(connection.getProvider());

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath(provider.getCodeUri())
                .queryParam("scope", escaper.escape(String.join(" ", addedScopes)))
                .queryParam("state", connection.getId())
                .queryParam("redirect_uri", redirectUri)
                .queryParam("client_id", provider.getClientId())
                .queryParam("response_type", "code");
        provider.getExtraQueryParams().forEach(builder::queryParam);
        return builder.build(false).toUriString();
    }

    public String buildAccessTokenUri(OAuth2Provider provider, String code) {
        return UriComponentsBuilder
                .fromUriString(provider.getTokenUri())
                .queryParam("client_id", provider.getClientId())
                .queryParam("client_secret", provider.getClientSecret())
                .queryParam("code", code)
                .queryParam("grant_type", "authorization_code")
                .queryParam("redirect_uri", redirectUri)
                .build()
                .toUriString();
    }

    public String buildRefreshTokenUri(OAuth2ExternalConnection connection) {
        OAuth2Provider provider = (OAuth2Provider) Hibernate.unproxy(connection.getProvider());
        return UriComponentsBuilder
                .fromUriString(provider.getTokenUri())
                .queryParam("client_id", provider.getClientId())
                .queryParam("client_secret", provider.getClientSecret())
                .queryParam("grant_type", "refresh_token")
                .queryParam("refresh_token", connection.getRefreshToken())
                .build()
                .toUriString();
    }
}
