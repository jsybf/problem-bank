package gitp.problembank.domain;

import java.time.Year;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("year_tag")
@Getter
public class YearTag {

	@Id
	@GeneratedValue
	private String id;

	@Setter
	private Year year;

	private YearTag(Year year) {
		this.year = year;
	}

	public static YearTag of(Year year) {
		return new YearTag(year);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		YearTag yearTag = (YearTag) o;

		return year.equals(yearTag.year);
	}

	@Override
	public int hashCode() {
		return year.hashCode();
	}
}
