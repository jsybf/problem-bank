package gitp.problembank.domain;

import java.time.Year;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("year_tag")
@Getter
public class YearTag {

	@Id
	@GeneratedValue
	private String id;

	private Year year;

	private YearTag(String id, Year year) {
		this.id = id;
		this.year = year;
	}

	public static YearTag of(Year year) {
		return new YearTag(null, year);
	}
}
