package ulitsa.raskolnikova.connections.dto.connection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import ulitsa.raskolnikova.connections.dto.connection.impl.ApiKeyExternalConnectionDto;
import ulitsa.raskolnikova.connections.dto.connection.impl.BasicExternalConnectionDto;
import ulitsa.raskolnikova.connections.dto.connection.impl.OAuth2ExternalConnectionDto;
import ulitsa.raskolnikova.connections.dto.connection.util.ExternalConnectionResponse;

import java.util.UUID;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "external_connection_type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OAuth2ExternalConnectionDto.class, name = "OAUTH2"),
        @JsonSubTypes.Type(value = ApiKeyExternalConnectionDto.class, name = "API_KEY"),
        @JsonSubTypes.Type(value = BasicExternalConnectionDto.class, name = "BASIC")
})
public abstract class ExternalConnectionDto implements ExternalConnectionResponse {
    private UUID id;
    private String name;
    @JsonProperty("provider_id")
    private UUID providerId;
    @JsonProperty("user_id")
    private UUID userId;
    private Boolean connected;
}
