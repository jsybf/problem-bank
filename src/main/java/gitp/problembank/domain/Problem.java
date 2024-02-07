package gitp.problembank.domain;

import gitp.problembank.domain.tag.SkillTag;
import gitp.problembank.domain.tag.TailUnitTag;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Node("problem")
@Getter
public class Problem {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;

    @Setter private String name;

    @Setter private Long rdbmsId;

    @Setter
    @Relationship(type = "related_problem")
    private Set<Problem> relatedProblemSet = new HashSet<>();

    @Setter
    @Relationship(type = "tagged")
    private Set<SkillTag> skillTagSet = new HashSet<>();

    @Setter
    @Relationship(type = "source")
    private ProblemSource problemSource;

    @Setter
    @Relationship(type = "tagged")
    private TailUnitTag tailUnitTag;


    public Problem(String name, Long rdbmsId, ProblemSource problemSource, TailUnitTag tailUnitTag) {
        this.name = name;
        this.rdbmsId = rdbmsId;
        this.problemSource = problemSource;
        this.tailUnitTag = tailUnitTag;
    }

    public static Problem of(String name, Long rdbmsId, ProblemSource problemSource) {
        return new Problem(name, rdbmsId, problemSource, null);
    }

    public static Problem of(String name, Long rdbmsId, ProblemSource problemSource, TailUnitTag tailUnitTag) {
        return new Problem(name, rdbmsId, problemSource, tailUnitTag);
    }

    public void addRelatedProblem(Problem... problems) {
        relatedProblemSet.addAll(Arrays.asList(problems));
        Arrays.asList(problems).forEach(problem -> problem.relatedProblemSet.add(this));
    }

    public void removeRelatedProblem(Problem problem) {
        relatedProblemSet.removeIf(p -> p.getName().equals(problem.getName()));
        problem.relatedProblemSet.removeIf(p -> p.getName().equals(this.getName()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Problem problem = (Problem) o;

        return name.equals(problem.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
