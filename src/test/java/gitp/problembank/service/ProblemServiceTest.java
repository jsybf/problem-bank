package gitp.problembank.service;

import gitp.problembank.domain.Problem;
import gitp.problembank.domain.ProblemSource;
import gitp.problembank.domain.ProblemSourceType;
import gitp.problembank.domain.tag.SkillTag;
import gitp.problembank.domain.tag.YearTag;
import gitp.problembank.dto.domain.ProblemDto;
import gitp.problembank.dto.domain.SkillTagDto;
import gitp.problembank.repository.ProblemRepository;
import gitp.problembank.utils.Neo4jTestSupporter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Year;

@SpringBootTest
class ProblemServiceTest extends Neo4jTestSupporter {

    @Autowired ProblemRepository problemRepository;
    @Autowired ProblemService problemService;

    @Test
    void skillTag_update_test() throws Exception {
        // given
        ProblemSource ps =
                ProblemSource.of("ps", ProblemSourceType.KICE, YearTag.of(Year.of(2023)));
        Problem p1 = Problem.of("p1", 1L, ps);
        SkillTag s1 = SkillTag.of("s1");
        SkillTag s2 = SkillTag.of("s2");

        p1.getSkillTagSet().add(s1);

        problemRepository.save(p1);
        ProblemDto p1Dto = ProblemDto.toDto(p1);
        // when
        p1Dto.getSkillTagDtoSet().removeIf(dto -> dto.getId() == s1.getId());
        p1Dto.getSkillTagDtoSet().add(SkillTagDto.toDto(s2));
        Problem updatedP1 = problemService.shallowUpdate(p1.getId(), p1Dto);

        // then
        Assertions.assertThat(updatedP1.getSkillTagSet()).contains(s2).hasSize(1);
    }

    @Test
    void related_problem_update_test() throws Exception {
        // given
        ProblemSource ps =
                ProblemSource.of("ps", ProblemSourceType.KICE, YearTag.of(Year.of(2023)));
        Problem p1 = Problem.of("p1", 1L, ps);
        Problem p2 = Problem.of("p2", 2L, ps);
        Problem p3 = Problem.of("p3", 3L, ps);
        Problem p4 = Problem.of("p4", 4L, ps);
        p1.addRelatedProblem(p2, p3);
        p2.addRelatedProblem(p3);
        p3.addRelatedProblem(p4);

        Problem new1 = Problem.of("new1", 1L, ps);
        Problem new2 = Problem.of("new2", 2L, ps);
        new1.addRelatedProblem(new2);

        problemRepository.save(p1);
        problemRepository.save(new1);

        ProblemDto p1Dto = ProblemDto.toDto(p1);
        ProblemDto new1Dto = ProblemDto.toDto(new1);

        p1Dto.setName("renamed");

        p1Dto.getRelatedProblemDtoSet().add(new1Dto);
        new1Dto.getRelatedProblemDtoSet().add(p1Dto);

        // when
        problemService.shallowUpdate(p1.getId(), p1Dto);
        // then
        System.out.println();
    }
}
