package ulitsa.raskolnikova.connections.entity.provider;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthType {
    OAUTH2("oauth2"),
    BASIC("basic"),
    API_KEY("api_key");

    private final String name;
}
