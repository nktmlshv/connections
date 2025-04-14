package ulitsa.raskolnikova.connections.dto.connection.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ulitsa.raskolnikova.connections.dto.connection.ExternalConnectionDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApiKeyExternalConnectionDto extends ExternalConnectionDto {
    @JsonProperty("api_key")
    private String apiKey;
}
