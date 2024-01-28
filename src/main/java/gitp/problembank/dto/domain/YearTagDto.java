package gitp.problembank.dto.domain;

import gitp.problembank.domain.YearTag;
import java.time.Year;
import lombok.Getter;

@Getter
public class YearTagDto {

	private final Year year;

	public YearTagDto(Year year) {
		this.year = year;
	}

	public YearTag toEntity() {
		return YearTag.of(year);
	}

	public static YearTagDto toDto(YearTag entity) {
		return new YearTagDto(entity.getYear());
	}
}
