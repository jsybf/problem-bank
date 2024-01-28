package gitp.problembank.dto.domain;

import gitp.problembank.domain.YearTag;

import lombok.Getter;

import java.time.Year;

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
