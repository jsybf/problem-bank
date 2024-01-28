package gitp.problembank.dto.domain;

import gitp.problembank.domain.Problem;
import gitp.problembank.domain.SkillTag;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class ProblemDto {

	private final String name;

	private final Long rdbmsId;

	private final Set<ProblemDto> relatedProblemDtoSet;

	private final Set<SkillTagDto> skillTagDtoSet;

	private final ProblemSourceDto problemSourceDto;


	public ProblemDto(String name, Long rdbmsId, Set<ProblemDto> relatedProblemDtoSet,
		Set<SkillTagDto> skillTagDtoSet, ProblemSourceDto problemSourceDto) {
		this.name = name;
		this.rdbmsId = rdbmsId;
		this.relatedProblemDtoSet = relatedProblemDtoSet;
		this.skillTagDtoSet = skillTagDtoSet;
		this.problemSourceDto = problemSourceDto;
	}

	private static ProblemDto toDtoExceptRelatedProblemDtoSet(Problem entity) {
		Set<SkillTagDto> skillTagDtos = entity.getSkillTagSet().stream().map(SkillTagDto::toDto)
			.collect(Collectors.toSet());
		ProblemDto dto = new ProblemDto(entity.getName(),
			entity.getRdbmsId(),
			new HashSet<ProblemDto>(),
			skillTagDtos,
			ProblemSourceDto.toDto(entity.getProblemSource()));
		return dto;
	}

	private static void toDtoByDfs(Problem entity, ProblemDto dto,
		Map<Problem, ProblemDto> entityDtoMap) {
		for (Problem relatedEntity : entity.getRelatedProblemSet()) {
			if (!entityDtoMap.containsKey(relatedEntity)) {
				//create dto
				ProblemDto relatedDto = toDtoExceptRelatedProblemDtoSet(relatedEntity);
				entityDtoMap.put(relatedEntity, relatedDto);
				dto.getRelatedProblemDtoSet().add(relatedDto);
				toDtoByDfs(relatedEntity, relatedDto, entityDtoMap);
			} else {
				dto.getRelatedProblemDtoSet().add(entityDtoMap.get(relatedEntity));
			}
		}
	}

	private static Problem toEntityExceptRelatedProblemSet(ProblemDto dto) {
		Problem entity = Problem.of(dto.name, dto.rdbmsId, dto.problemSourceDto.toEntity());

		Set<SkillTag> skillTagSet = dto.skillTagDtoSet.stream().map(SkillTagDto::toEntity)
			.collect(Collectors.toSet());
		entity.getSkillTagSet().addAll(skillTagSet);

		return entity;
	}

	private static void toEntityByDfs(ProblemDto dto, Problem entity,
		Map<ProblemDto, Problem> dtoEntityMap) {
		for (ProblemDto relatedDto : dto.relatedProblemDtoSet) {
			if (!dtoEntityMap.containsKey(relatedDto)) {
				Problem relatedEntity = toEntityExceptRelatedProblemSet(relatedDto);
				dtoEntityMap.put(relatedDto, relatedEntity);
				entity.getRelatedProblemSet().add(relatedEntity);
				toEntityByDfs(relatedDto, relatedEntity, dtoEntityMap);
			} else {
				entity.getRelatedProblemSet().add(dtoEntityMap.get(relatedDto));
			}
		}
	}

	public static ProblemDto toDto(Problem entity) {

		ProblemDto dto = toDtoExceptRelatedProblemDtoSet(entity);
		Map<Problem, ProblemDto> entityDtoMap = new HashMap<>();
		entityDtoMap.put(entity, dto);
		toDtoByDfs(entity, dto, entityDtoMap);
		return dto;
	}


	public Problem toEntity() {
		Problem entity = toEntityExceptRelatedProblemSet(this);
		Map<ProblemDto, Problem> dtoEntityMap = new HashMap<>();
		dtoEntityMap.put(this, entity);
		toEntityByDfs(this, entity, dtoEntityMap);
		return entity;
	}
}
