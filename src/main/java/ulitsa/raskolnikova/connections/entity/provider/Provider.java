package ulitsa.raskolnikova.connections.entity.provider;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "provider")
@Inheritance(strategy = InheritanceType.JOINED)
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "auth_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthType authType;
}
