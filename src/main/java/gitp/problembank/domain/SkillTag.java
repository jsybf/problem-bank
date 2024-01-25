package gitp.problembank.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("skill_tag")
@Getter
public class SkillTag {

	@Id
	@GeneratedValue
	private String id;

	@Setter
	private String title;

	private SkillTag(String title) {
		this.title = title;
	}

	public static SkillTag of(String title) {
		return new SkillTag(title);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SkillTag skillTag = (SkillTag) o;

		return title.equals(skillTag.title);
	}

	@Override
	public int hashCode() {
		return title.hashCode();
	}
}
