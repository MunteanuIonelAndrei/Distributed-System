package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2020.entities.PersAux;

import java.util.Optional;
import java.util.UUID;

public interface PersAuxRepository extends JpaRepository<PersAux, UUID> {

    Optional<PersAux> findPersAuxById(UUID id);
}
