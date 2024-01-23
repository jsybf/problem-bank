package gitp.problembank.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Node("problem")
@Getter
public class Problem {

	@Id
	@GeneratedValue(UUIDStringGenerator.class)
	private String id;

	private String name;

	private Long rdbmsId;

	@Relationship(type = "related_problem")
	private Set<Problem> relatedProblemSet = new HashSet<>();

	@Relationship(type = "tagged")
	private Set<SkillTag> skillTagSet = new HashSet<>();

	@Setter
	@Relationship(type = "source")
	private ProblemSource problemSource;

	public void addRelatedProblem(Problem... problems) {
		relatedProblemSet.addAll(Arrays.asList(problems));
		Arrays.asList(problems).forEach(problem -> problem.getRelatedProblemSet().add(this));
	}

	public void removeRelatedProblem(Problem problem) {
		relatedProblemSet.removeIf(p -> p.getName().equals(problem.getName()));
		problem.getRelatedProblemSet().removeIf(p -> p.getName().equals(this.getName()));
	}

	private Problem(String id, String name, Long rdbmsId) {
		this.id = id;
		this.name = name;
		this.rdbmsId = rdbmsId;
	}

	public static Problem of(String name, Long rdbmsId) {
		return new Problem(null, name, rdbmsId);
	}
}
