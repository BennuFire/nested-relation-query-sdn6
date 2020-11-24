package io.github.fbiville.nestedrelationquerysdn6;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import java.util.ArrayList;
import java.util.List;

@Node
public class ChangesetExtended extends Changeset {

    private String cc;

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}
    
}