package io.github.fbiville.nestedrelationquerysdn6;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Node(primaryLabel = "Changeset")
public class Changeset extends HypercubeNode {

    @Property("USER")
    private String user;

    @Relationship(direction = Direction.OUTGOING)
    private Map<String, List<ChangesetIn>> changesetIn = new HashMap<>(); 
    
    @Relationship(type = "CHANGESET_IN", direction = Direction.OUTGOING) 
    private List<Workspace> workspace = new ArrayList<>();


    public List<Workspace> getWorkspace() {
		return workspace;
	}

	public void setWorkspace(List<Workspace> workspace) {
		this.workspace = workspace;
	}

	public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

     public Map<String, List<ChangesetIn>> getChangesetIn() {
        return changesetIn;
    }

    public void setChangesetIn(Map<String, List<ChangesetIn>> changesetIn) {
        this.changesetIn = changesetIn;
    }  
}
