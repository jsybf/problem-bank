package gitp.problembank.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Node("problem_source")
@Getter
public class ProblemSource {

	@Id
	@GeneratedValue(UUIDStringGenerator.class)
	private String id;

	@Setter
	private String name;

	@Setter
	private ProblemSourceType sourceType;

	@Setter
	@Relationship(type = "tagged")
	private YearTag yearTag;

	public ProblemSource(String name, ProblemSourceType sourceType, YearTag yearTag) {
		this.name = name;
		this.sourceType = sourceType;
		this.yearTag = yearTag;
	}

	public static ProblemSource of(String name, ProblemSourceType sourceType, YearTag yearTag) {
		return new ProblemSource(name, sourceType, yearTag);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ProblemSource that = (ProblemSource) o;

		if (sourceType != that.sourceType) {
			return false;
		}
		return yearTag.equals(that.yearTag);
	}

	@Override
	public int hashCode() {
		int result = sourceType.hashCode();
		result = 31 * result + yearTag.hashCode();
		return result;
	}
}
