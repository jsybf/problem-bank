package gitp.problembank.dto.domain;

import gitp.problembank.domain.Problem;
import gitp.problembank.domain.SkillTag;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProblemDto {

    @Setter(AccessLevel.PRIVATE)
    private String id;

    private String name;

    private Long rdbmsId;

    @Setter(AccessLevel.PRIVATE)
    private Set<ProblemDto> relatedProblemDtoSet;

    @Setter(AccessLevel.PRIVATE)
    private Set<SkillTagDto> skillTagDtoSet;

    private ProblemSourceDto problemSourceDto;

    private ProblemDto(
            String id,
            String name,
            Long rdbmsId,
            Set<ProblemDto> relatedProblemDtoSet,
            Set<SkillTagDto> skillTagDtoSet,
            ProblemSourceDto problemSourceDto) {
        this.id = id;
        this.name = name;
        this.rdbmsId = rdbmsId;
        this.relatedProblemDtoSet = relatedProblemDtoSet;
        this.skillTagDtoSet = skillTagDtoSet;
        this.problemSourceDto = problemSourceDto;
    }

    public static ProblemDto of(
            String id,
            String name,
            Long rdbmsId,
            Set<ProblemDto> relatedProblemDtoSet,
            Set<SkillTagDto> skillTagDtoSet,
            ProblemSourceDto problemSourceDto) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        return new ProblemDto(
                id, name, rdbmsId, relatedProblemDtoSet, skillTagDtoSet, problemSourceDto);
    }

    private static ProblemDto toDtoExceptRelatedProblemDtoSet(Problem entity) {
        Set<SkillTagDto> skillTagDtos =
                entity.getSkillTagSet().stream()
                        .map(SkillTagDto::toDto)
                        .collect(Collectors.toSet());
        return ProblemDto.of(
                entity.getId(),
                entity.getName(),
                entity.getRdbmsId(),
                new HashSet<ProblemDto>(),
                skillTagDtos,
                ProblemSourceDto.toDto(entity.getProblemSource()));
    }

    private static void toDtoByDfs(
            Problem entity, ProblemDto dto, Map<Problem, ProblemDto> entityDtoMap) {
        for (Problem relatedEntity : entity.getRelatedProblemSet()) {
            if (!entityDtoMap.containsKey(relatedEntity)) {
                // create dto
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

        Set<SkillTag> skillTagSet =
                dto.skillTagDtoSet.stream().map(SkillTagDto::toEntity).collect(Collectors.toSet());
        entity.getSkillTagSet().addAll(skillTagSet);

        return entity;
    }

    private static void toEntityByDfs(
            ProblemDto dto, Problem entity, Map<ProblemDto, Problem> dtoEntityMap) {
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

    public void addRelatedProblemDto(ProblemDto... dtos) {
        List.of(dtos).forEach(dto -> dto.getRelatedProblemDtoSet().add(this));
        relatedProblemDtoSet.addAll(List.of(dtos));
    }

    public void removeRelatedProblemDto(ProblemDto problemDto) {
        assert problemDto.getId() != null && id != null;

        boolean removedCompletely =
                problemDto.getRelatedProblemDtoSet().removeIf(dto -> dto.getId() == id)
                        && relatedProblemDtoSet.removeIf(dto -> dto.getId() == problemDto.getId());

        if (!removedCompletely) {
            throw new IllegalStateException("remove failed");
        }
    }
}
