package ulitsa.raskolnikova.connections.dto.provider;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import ulitsa.raskolnikova.connections.dto.provider.impl.OAuth2ProviderDto;
import ulitsa.raskolnikova.connections.entity.provider.AuthType;

import java.util.UUID;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "auth_type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OAuth2ProviderDto.class, name = "OAUTH2"),
        @JsonSubTypes.Type(value = ProviderDto.class, name = "BASIC"),
        @JsonSubTypes.Type(value = ProviderDto.class, name = "API_KEY")
})
public class ProviderDto {
    private UUID id;
    private String name;
    @JsonProperty("auth_type")
    private AuthType authType;
}
