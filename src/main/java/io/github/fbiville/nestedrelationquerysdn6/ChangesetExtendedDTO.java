package io.github.fbiville.nestedrelationquerysdn6;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import java.util.ArrayList;
import java.util.List;

public class ChangesetExtendedDTO  {

    private String cc;
    
    public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	
	private Changeset changeset;
	 
	public Changeset getChangeset() {
		return changeset;
	}
	public void setChangeset(Changeset changeset) {
		this.changeset = changeset;
	}
	
    
}