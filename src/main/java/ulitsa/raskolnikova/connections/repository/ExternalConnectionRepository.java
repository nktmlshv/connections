package ulitsa.raskolnikova.connections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ulitsa.raskolnikova.connections.entity.connection.ExternalConnection;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExternalConnectionRepository extends JpaRepository<ExternalConnection, UUID> {
    List<ExternalConnection> findAllByUserId(UUID userId);
    boolean existsByUserIdAndProviderId(UUID userId, UUID providerId);
}
