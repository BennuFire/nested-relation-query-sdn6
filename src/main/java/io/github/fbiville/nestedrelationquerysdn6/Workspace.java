package io.github.fbiville.nestedrelationquerysdn6;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Node(primaryLabel = "Workspace")
public class Workspace extends HypercubeNode {

    

    @Relationship(direction = Direction.OUTGOING)
    private Map<String, List<WorkspaceIn>> workspaceIn = new HashMap<>();

    //Different Queries this Property exists
    @Relationship(type = "WORKSPACE_IN", direction = Direction.OUTGOING) 
    private List<Work> work = new ArrayList<>();
    
    public Map<String, List<WorkspaceIn>> getWorkspaceIn() {
        return workspaceIn;
    }

    public void setWorkspaceIn(Map<String, List<WorkspaceIn>> workspaceIn) {
        this.workspaceIn = workspaceIn;
    }

	public List<Work> getWork() {
		return work;
	}

	public void setWork(List<Work> work) {
		this.work = work;
	} 
    
    
}
