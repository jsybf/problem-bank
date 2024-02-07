package gitp.problembank.dto;

import gitp.problembank.domain.ProblemSourceType;

import lombok.Getter;
import lombok.Setter;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ProblemResearchParamDto {
    Set<String> skillTagIds = new HashSet<>();

    Year year;
    YearResearchingType yearResearchingType;

    Set<ProblemSourceType> problemSourceTypes = new HashSet<>();
    Set<String> problemSourceIds = new HashSet<>();

    Set<String> unitTagIds = new HashSet<>();

    public ProblemResearchParamDto(
            Set<String> skillTagIds,
            Year year,
            YearResearchingType yearResearchingType,
            Set<ProblemSourceType> problemSourceTypes,
            Set<String> problemSourceIds,
            Set<String> unitTagIds) {
        this.skillTagIds = skillTagIds;
        this.year = year;
        this.yearResearchingType = yearResearchingType;
        this.problemSourceTypes = problemSourceTypes;
        this.problemSourceIds = problemSourceIds;
        this.unitTagIds = unitTagIds;
    }

    public static ProblemResearchParamDto of(
            Set<String> skillTagIds,
            Year year,
            YearResearchingType yearResearchingType,
            Set<ProblemSourceType> problemSourceTypes,
            Set<String> problemSourceIds,
            Set<String> unitTagIds) {
        return new ProblemResearchParamDto(
                skillTagIds,
                year,
                yearResearchingType,
                problemSourceTypes,
                problemSourceIds,
                unitTagIds);
    }
}
