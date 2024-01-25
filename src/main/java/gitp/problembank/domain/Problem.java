package gitp.problembank.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
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

	@Setter
	private String name;

	@Setter
	private Long rdbmsId;

	@Setter
	@Getter(AccessLevel.PRIVATE)
	@Relationship(type = "related_problem")
	private Set<Problem> relatedProblemSet = new HashSet<>();

	@Setter
	@Relationship(type = "tagged")
	private Set<SkillTag> skillTagSet = new HashSet<>();

	@Setter
	@Relationship(type = "source")
	private ProblemSource problemSource;

	public Problem(String name, Long rdbmsId, ProblemSource problemSource) {
		this.name = name;
		this.rdbmsId = rdbmsId;
		this.problemSource = problemSource;
	}

	public static Problem of(String name, Long rdbmsId, ProblemSource problemSource) {
		return new Problem(name, rdbmsId, problemSource);
	}

	public void addRelatedProblem(Problem... problems) {
		relatedProblemSet.addAll(Arrays.asList(problems));
		Arrays.asList(problems).forEach(problem -> problem.relatedProblemSet.add(this));
	}

	public void removeRelatedProblem(Problem problem) {
		relatedProblemSet.removeIf(p -> p.getName().equals(problem.getName()));
		problem.relatedProblemSet.removeIf(p -> p.getName().equals(this.getName()));
	}

	public Set<Problem> getRelatedProblemSet() {
		return Set.copyOf(relatedProblemSet);
	}
}