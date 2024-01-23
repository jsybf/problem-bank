package gitp.problembank.domain;

import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Node("problem_source")
public class ProblemSource {

	@Id
	@GeneratedValue(UUIDStringGenerator.class)
	private String id;

	private String name;

	private ProblemSourceType sourceType;

	@Setter
	@Relationship(type = "tagged")
	private YearTag yearTag;

	public ProblemSource(String name, ProblemSourceType sourceType) {
		this.name = name;
		this.sourceType = sourceType;
	}

	public static ProblemSource of(String name, ProblemSourceType sourceType) {
		return new ProblemSource(name, sourceType);
	}
}
