package gitp.problembank.service;

import gitp.problembank.domain.Problem;
import gitp.problembank.domain.ProblemSource;
import gitp.problembank.domain.ProblemSourceType;
import gitp.problembank.domain.tag.YearTag;
import gitp.problembank.repository.ProblemRepository;
import gitp.problembank.repository.ProblemSourceRepository;
import gitp.problembank.utils.Neo4jTestSupporter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Year;

@SpringBootTest
class ProblemSourceServiceTest extends Neo4jTestSupporter {

    @Autowired ProblemRepository problemRepository;

    @Autowired ProblemSourceRepository problemSourceRepository;

    @Test
    void check_related_problem_when_problem_source_deleted() throws Exception {
        // given
        ProblemSource ps1 =
                ProblemSource.of("ps1", ProblemSourceType.KICE, YearTag.of(Year.of(2023)));
        Problem p1 = Problem.of("p1", 1L, ps1);

        problemRepository.save(p1);
        // when
        problemSourceRepository.deleteById(ps1.getId());
        Problem foundP1 = problemRepository.findById(p1.getId()).orElseThrow();
        // then
        Assertions.assertThat(foundP1.getProblemSource()).isNull();
    }
}
