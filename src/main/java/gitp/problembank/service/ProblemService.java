package gitp.problembank.service;

import gitp.problembank.domain.Problem;
import gitp.problembank.domain.tag.SkillTag;
import gitp.problembank.dto.domain.ProblemDto;
import gitp.problembank.dto.domain.SkillTagDto;
import gitp.problembank.repository.ProblemRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProblemService {

    private final ProblemRepository problemRepository;

    @Transactional(readOnly = true)
    public Optional<ProblemDto> findById(String id) {
        return problemRepository.findById(id).map(ProblemDto::toDto);
    }

    public void save(ProblemDto problemDto) {
        if (problemDto.getId() != null) {
            throw new IllegalArgumentException("problemDto should refer not persisted entity");
        }
        problemRepository.save(problemDto.toEntity());
    }

    /**
     * doesn't modify value of referring entities just change / update / remove these only modify
     * related problem below 1 depth!!!
     *
     * @param problemId
     * @param problemDto
     */
    public Problem shallowUpdate(String problemId, ProblemDto problemDto) {
        Problem foundProblem =
                problemRepository
                        .findById(problemId)
                        .orElseThrow(
                                () ->
                                        new NoSuchElementException(
                                                "problem entity not found id:" + problemId));

        foundProblem.setName(problemDto.getName());
        foundProblem.setRdbmsId(problemDto.getRdbmsId());

        // update SkillTag
        Map<String, SkillTag> idSkillTagMap =
                foundProblem.getSkillTagSet().stream()
                        .collect(
                                Collectors.toMap(
                                        skillTag -> skillTag.getId(), skillTag -> skillTag));
        Map<String, SkillTagDto> idSkillDtoTagMap =
                problemDto.getSkillTagDtoSet().stream()
                        .collect(Collectors.toMap(dto -> dto.getId(), dto -> dto));

        Set<SkillTag> newSkillTag =
                idSkillDtoTagMap.entrySet().stream()
                        .filter(e -> !idSkillTagMap.containsKey(e.getKey()))
                        .map(Entry::getValue)
                        .map(SkillTagDto::toEntity)
                        .collect(Collectors.toSet());

        Set<String> removeSkillTagIds =
                idSkillTagMap.entrySet().stream()
                        .filter(e -> !idSkillDtoTagMap.containsKey(e.getKey()))
                        .map(e -> e.getValue().getId())
                        .collect(Collectors.toSet());

        foundProblem
                .getSkillTagSet()
                .removeIf(skillTag -> removeSkillTagIds.contains(skillTag.getId()));
        foundProblem.getSkillTagSet().addAll(newSkillTag);

        // update relatedProblem
        Map<String, Problem> idProblemMap =
                foundProblem.getRelatedProblemSet().stream()
                        .collect(Collectors.toMap(problem -> problem.getId(), problem -> problem));

        Set<String> relatedProblemDtoIdSet =
                problemDto.getRelatedProblemDtoSet().stream()
                        .map(dto -> dto.getId())
                        .collect(Collectors.toSet());

        //  remove relatedProblem
        for (Entry<String, Problem> idProblemEntry : idProblemMap.entrySet()) {
            if (!relatedProblemDtoIdSet.contains(idProblemEntry.getKey())) {
                idProblemEntry
                        .getValue()
                        .getRelatedProblemSet()
                        .removeIf(problem -> problem.getId() == foundProblem.getId());
                foundProblem
                        .getRelatedProblemSet()
                        .removeIf(problem -> problem.getId() == idProblemEntry.getKey());
            }
        }
        //  add relatedProblem
        relatedProblemDtoIdSet.stream()
                .filter(dtoId -> !idProblemMap.containsKey(dtoId))
                .map(dtoId -> problemRepository.findById(dtoId).orElseThrow())
                .forEach(foundProblem::addRelatedProblem);

        return problemRepository.save(foundProblem);
    }
}
