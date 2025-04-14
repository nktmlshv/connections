package ulitsa.raskolnikova.connections.entity.connection.impl;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ulitsa.raskolnikova.connections.entity.connection.ExternalConnection;

import java.time.Instant;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "oauth2_external_connection")
public class OAuth2ExternalConnection extends ExternalConnection {
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "expires_at")
    private Instant expiresAt;
    @Column(name = "refresh_token")
    private String refreshToken;
    @ElementCollection
    @CollectionTable(name = "available_scope")
    private Set<String> scopes;
    @Column(name = "token_type")
    private String tokenType;
}
