package ulitsa.raskolnikova.connections.dto.connection.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ulitsa.raskolnikova.connections.dto.connection.ExternalConnectionDto;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class OAuth2ExternalConnectionDto extends ExternalConnectionDto {
    private Set<String> scopes;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
}
