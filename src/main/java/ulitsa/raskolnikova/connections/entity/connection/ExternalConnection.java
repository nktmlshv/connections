package ulitsa.raskolnikova.connections.entity.connection;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ulitsa.raskolnikova.connections.entity.provider.Provider;

import java.util.UUID;

@Data
@Entity
@Table(name = "external_connection")
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class ExternalConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "сonnected")
    private Boolean connected;
}
