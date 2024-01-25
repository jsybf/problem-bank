package gitp.problembank.dto.domain;

import gitp.problembank.domain.ProblemSource;
import gitp.problembank.domain.ProblemSourceType;
import lombok.Getter;

@Getter
public class ProblemSourceDto {

	private final String name;
	private final ProblemSourceType sourceType;
	private final YearTagDto yearTagDto;

	public ProblemSourceDto(String name, ProblemSourceType sourceType,
		YearTagDto yearTagDto) {
		this.name = name;
		this.sourceType = sourceType;
		this.yearTagDto = yearTagDto;
	}

	public static ProblemSourceDto toDto(ProblemSource entity) {
		return new ProblemSourceDto(entity.getName(), entity.getSourceType(),
			YearTagDto.toDto(entity.getYearTag()));
	}

	public ProblemSource toEntity() {
		return new ProblemSource(name, sourceType, yearTagDto.toEntity());
	}
}
