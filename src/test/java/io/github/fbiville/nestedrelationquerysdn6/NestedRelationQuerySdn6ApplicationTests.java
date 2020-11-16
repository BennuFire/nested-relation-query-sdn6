package io.github.fbiville.nestedrelationquerysdn6;

import org.assertj.core.util.Maps;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataNeo4jTest
@TestInstance(Lifecycle.PER_CLASS)
class NestedRelationQuerySdn6ApplicationTests {

    @Autowired
    private Driver driver;

    //@Container
    //private static final Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j:4.0");

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        //registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl);
        registry.add("spring.neo4j.uri", () -> "bolt://localhost:7687");
        //registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        //registry.add("spring.neo4j.authentication.password", neo4jContainer::getAdminPassword);
    }

    @BeforeAll
    void prepare(@Autowired ChangesetRepository repository) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (n) DETACH DELETE n");
                //tx.run("CREATE (:Workspace:Hypercube {STATE: 'some-xxx', ID : 1 , CANCELED : false})<-[:CHANGESET_IN {VALID : true}]-(:Changeset:Hypercube {USER: 'some-user', ID: 1, CANCELED : false})-[:CHANGESET_IN {VALID : true}]->(ws:Workspace:Hypercube {STATE: 'some-state', ID : 1 , CANCELED : false}), " +
                tx.run("CREATE (:Changeset:Hypercube {USER: 'some-user', ID : 1, CANCELED : false})-[:CHANGESET_IN {VALID : true}]->(ws:Workspace:Hypercube {STATE: 'some-state', ID : 1 , CANCELED : false}), " +
                        "     (ws)-[:WORKSPACE_IN]->(:Hypercube:Work {STATE: 'some-other-state', ID : 1 , CANCELED : false})");
                return null;
            });
        }
    }

    @Test
    void finds_changeset_by_id_rels_rels(@Autowired ChangesetRepository repository) {
        Long changesetId = findChangesetId("some-user");

        Optional<Changeset> result = repository.findByIdAndCanceledFalse(changesetId);

        assertThat(result).hasValueSatisfying((changeset) -> {
            assertThat(changeset.getId()).isEqualTo(changesetId);
            assertThat(changeset.getUser()).isEqualTo("some-user");
            Map<String, List<ChangesetIn>> changesetRels = changeset.getChangesetIn();
            assertThat(changesetRels).hasSize(1);
            String key = changesetRels.keySet().iterator().next();
            Workspace workspace = (Workspace) changesetRels.get(key).get(0).getWorkspace();
            assertThat(workspace.getState()).isEqualTo("some-state");
            Map<String, List<WorkspaceIn>> workspaceRels = workspace.getWorkspaceIn();
            assertThat(workspaceRels).hasSize(1);
            key = workspaceRels.keySet().iterator().next();
            Work work = (Work) workspaceRels.get(key).get(0).getWork();
            assertThat(work.getState()).isEqualTo("some-other-state");
        }); 
    }
    
    @Test
    void finds_changeset_by_id_rels_list(@Autowired ChangesetRepository repository) {
        Long changesetId = findChangesetId("some-user");

        Optional<Changeset> result = repository.findByIdAndCanceledFalse(changesetId);

        assertThat(result).hasValueSatisfying((changeset) -> {
        	assertThat(changeset.getId()).isEqualTo(changesetId);
            assertThat(changeset.getUser()).isEqualTo("some-user");
            Map<String, List<ChangesetIn>> changesetRels = changeset.getChangesetIn();
            assertThat(changesetRels).hasSize(1);
            String key = changesetRels.keySet().iterator().next();
            Workspace workspace = (Workspace) changesetRels.get(key).get(0).getWorkspace();
            assertThat(workspace.getState()).isEqualTo("some-state");
            List<Work> works = workspace.getWork();
            assertThat(works).hasSize(1);
            Work work = works.get(0);
            assertThat(work.getState()).isEqualTo("some-other-state");
        }); 
    }

    @Test
    void finds_changeset_by_id_list_rels(@Autowired ChangesetRepository repository) {
        Long changesetId = findChangesetId("some-user");

        Optional<Changeset> result = repository.findByIdAndCanceledFalse(changesetId);

        assertThat(result).hasValueSatisfying((changeset) -> {
        	assertThat(changeset.getId()).isEqualTo(changesetId);
            assertThat(changeset.getUser()).isEqualTo("some-user");
            List<Workspace> workspaces = changeset.getWorkspace();
            assertThat(workspaces).hasSize(1);
            Workspace workspace = workspaces.get(0);
            assertThat(workspace.getState()).isEqualTo("some-state");
            Map<String, List<WorkspaceIn>> workspaceRels = workspace.getWorkspaceIn();
            assertThat(workspaceRels).hasSize(1);
            String key = workspaceRels.keySet().iterator().next();
            Work work = (Work) workspaceRels.get(key).get(0).getWork();
            assertThat(work.getState()).isEqualTo("some-other-state");
        }); 
    }
    
    @Test
    void finds_changeset_by_id_list_list(@Autowired ChangesetRepository repository) {
        Long changesetId = findChangesetId("some-user");

        Optional<Changeset> result = repository.findByIdAndCanceledFalse(changesetId);

        assertThat(result).hasValueSatisfying((changeset) -> {
            assertThat(changeset.getId()).isEqualTo(changesetId);
            assertThat(changeset.getUser()).isEqualTo("some-user");
            List<Workspace> workspaces = changeset.getWorkspace();
            assertThat(workspaces).hasSize(1);
            Workspace workspace = workspaces.get(0);
            assertThat(workspace.getState()).isEqualTo("some-state");
            List<Work> works = workspace.getWork();
            assertThat(works).hasSize(1);
            Work work = works.get(0);
            assertThat(work.getState()).isEqualTo("some-other-state");
        }); 
    }

    private Long findChangesetId(String user) {
        try (Session session = driver.session()) {
            return session.readTransaction(tx -> {
                Result result = tx.run(
                        "MATCH (c:Changeset:Hypercube {USER: $user}) RETURN c.ID AS id",
                        Maps.newHashMap("user", user));
                return result.single().get("id").asLong();
            });
        }
    }

}
