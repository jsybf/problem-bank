package gitp.problembank.domain;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("skill_tag")
@Getter
public class SkillTag {

	@Id
	@GeneratedValue
	private String id;

	private String title;

	private SkillTag(String id, String title) {
		this.id = id;
		this.title = title;
	}

	public static SkillTag of(String title) {
		return new SkillTag(null, title);
	}
}
