package io.github.fbiville.nestedrelationquerysdn6;


import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;

public interface ChangesetRepository extends Neo4jRepository<Changeset, Long> {

    Optional<Changeset> findById(Long id);
    
    Optional<Changeset> findByIdAndCanceledFalse(Long id);
    
    @Query("MATCH (c:Changeset:Hypercube {ID: $id}) RETURN c, '1'AS cc")
    Optional<ChangesetExtended> findExtended(Long id);
    
    @Query("MATCH (c:Changeset {ID: $id}) RETURN c as changeset, '1'AS cc")
    Optional<ChangesetExtendedDTO> findExtendedDTO(Long id);
}
