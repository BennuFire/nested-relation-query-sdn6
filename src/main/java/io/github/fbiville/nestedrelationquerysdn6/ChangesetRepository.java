package io.github.fbiville.nestedrelationquerysdn6;


import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface ChangesetRepository extends Neo4jRepository<Changeset, Long> {

    Optional<Changeset> findById(Long id);
    
    Optional<Changeset> findByIdAndCanceledFalse(Long id);
}
