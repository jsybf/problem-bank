package gitp.problembank.dto.domain;

import gitp.problembank.domain.Problem;
import gitp.problembank.domain.ProblemSource;
import gitp.problembank.domain.ProblemSourceType;
import gitp.problembank.domain.YearTag;

import org.junit.jupiter.api.Test;

import java.time.Year;

class ProblemDtoTest {

    @Test
    void toDtoByDfs_test() throws Exception {
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
        // when
        ProblemDto dto = ProblemDto.toDto(p1);
        // then
        System.out.println();
    }

    @Test
    void toEntityByDfs_test() throws Exception {
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

        ProblemDto p1Dto = ProblemDto.toDto(p1);
        // when
        Problem p = p1Dto.toEntity();
        // then
        System.out.println();
    }
}
