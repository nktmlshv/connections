package ulitsa.raskolnikova.connections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ulitsa.raskolnikova.connections.entity.provider.Provider;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, UUID> {
    Optional<Provider> findProviderByName(String name);
}
