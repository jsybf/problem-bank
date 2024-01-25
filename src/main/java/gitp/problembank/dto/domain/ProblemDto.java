package gitp.problembank.dto.domain;

import gitp.problembank.domain.Problem;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public class ProblemDto {

	private final String name;

	private final Long rdbmsId;

	private final Set<ProblemDto> relatedProblemDtoSet = new HashSet<>();

	private final Set<SkillTagDto> skillTagDtoSet = new HashSet<>();

	private final ProblemSourceDto problemSourceDto;

	public ProblemDto(String name, Long rdbmsId, ProblemSourceDto problemSourceDto) {
		this.name = name;
		this.rdbmsId = rdbmsId;
		this.problemSourceDto = problemSourceDto;
	}

	private static ProblemDto toDtoRecursive(Problem entity, List<String> convertedEntityNameList) {
		//create dto for current entity
		ProblemDto problemDto = new ProblemDto(entity.getName(), entity.getRdbmsId(),
			ProblemSourceDto.toDto(entity.getProblemSource()));

		//recursive call for related entity of current entity
		Set<ProblemDto> problemDtoSet = new HashSet<>();
		for (Problem problem : entity.getRelatedProblemSet()) {
			//if problem.name is contained in convertedEntityNameList then continue
			if (convertedEntityNameList.contains(problem.getName())) {
				continue;
			}
			List<String> list = Stream.concat(convertedEntityNameList.stream(),
				Stream.of(entity.getName())).toList();
			problemDto.relatedProblemDtoSet.add(toDtoRecursive(problem, list));
		}

		return problemDto;
	}

	public static ProblemDto toDto(Problem entity) {
		return toDtoRecursive(entity, Collections.emptyList());
	}

//	public static ProblemDto toDto(Problem entity) {
//		ProblemDto problemDto = new ProblemDto(entity.getName(), entity.getRdbmsId(),
//			ProblemSourceDto.toDto(entity.getProblemSource()));
//		for (Problem relatedProblem : entity.getRelatedProblemSet()) {
//			for (Problem indirectRelatedProblem: relatedProblem.getRelatedProblemSet()) {
//				if(indirectRelatedProblem.equals(entity)) {
//					continue;
//				}
//
//			}
//		}
//		entity.getSkillTagSet()
//			.forEach(skillTag -> problemDto.skillTagDtoSet.add(SkillTagDto.toDto(skillTag)));
//		return problemDto;
//	}

	public Problem toEntity() {
		Problem problem = Problem.of(name, rdbmsId, problemSourceDto.toEntity());
		skillTagDtoSet.forEach(dto -> problem.getSkillTagSet().add(dto.toEntity()));
		problem.setRelatedProblemSet(
			relatedProblemDtoSet.stream().map(dto -> dto.toEntity()).collect(Collectors.toSet()));
		return problem;
	}

}
