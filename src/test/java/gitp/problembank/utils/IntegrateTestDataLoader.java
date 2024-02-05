package gitp.problembank.utils;

import gitp.problembank.domain.Problem;
import gitp.problembank.domain.ProblemSource;
import gitp.problembank.domain.ProblemSourceType;
import gitp.problembank.domain.tag.SkillTag;
import gitp.problembank.domain.tag.YearTag;
import gitp.problembank.repository.ProblemRepository;
import java.time.Year;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IntegrateTestDataLoader extends Neo4jTestSupporter {
    @Autowired ProblemRepository problemRepository;

    @Test
    void data_1() throws Exception {
        ProblemSource ps1 =
                ProblemSource.of("ps1", ProblemSourceType.KICE, YearTag.of(Year.of(2023)));

        ProblemSource ps2 =
                ProblemSource.of("ps2", ProblemSourceType.MOCK_TEST, YearTag.of(Year.of(2022)));

        SkillTag s1 = SkillTag.of("skill_tag_1");
        SkillTag s2 = SkillTag.of("skill_tag_2");
        SkillTag s3 = SkillTag.of("skill_tag_3");
        SkillTag s4 = SkillTag.of("skill_tag_4");

        Problem p1 = Problem.of("p1", 1L, ps1);
        Problem p2 = Problem.of("p2", 2L, ps1);
        Problem p3 = Problem.of("p3", 3L, ps1);
        Problem p4 = Problem.of("p4", 4L, ps2);
        Problem p5 = Problem.of("p5", 5L, ps2);
        Problem p6 = Problem.of("p6", 6L, ps2);

        p1.addRelatedProblem(p2, p3, p4, p5);
        p2.addRelatedProblem(p4, p6);
        p3.addRelatedProblem(p5, p6);
        p4.addRelatedProblem(p6);

        p1.getSkillTagSet().add(s1);
        p2.getSkillTagSet().addAll(List.of(s1, s3));
        p3.getSkillTagSet().addAll(List.of(s2, s4));
        p4.getSkillTagSet().addAll(List.of(s1, s2, s3));
        p5.getSkillTagSet().addAll(List.of(s1, s2, s4));
        p6.getSkillTagSet().addAll(List.of(s1, s2, s3, s4));

        problemRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6));
    }
}
