package ulitsa.raskolnikova.connections.dto.connection.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ulitsa.raskolnikova.connections.dto.connection.ExternalConnectionDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class BasicExternalConnectionDto extends ExternalConnectionDto {
    private String login;
    private String password;
}
