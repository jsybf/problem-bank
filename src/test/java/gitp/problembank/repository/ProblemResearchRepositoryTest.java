package gitp.problembank.repository;

import static org.junit.jupiter.api.Assertions.*;

import gitp.problembank.domain.ProblemSourceType;
import gitp.problembank.dto.ProblemResearchParamDto;
import gitp.problembank.dto.UnitChainDto;
import gitp.problembank.dto.YearResearchingType;
import gitp.problembank.utils.Neo4jTestSupporter;

import org.junit.jupiter.api.Test;
import org.neo4j.cypherdsl.core.ResultStatement;
import org.neo4j.cypherdsl.core.renderer.Renderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.Set;

@SpringBootTest
@Transactional(readOnly = true)
class ProblemResearchRepositoryTest extends Neo4jTestSupporter {
    @Autowired ProblemResearchRepository problemResearchRepository;
    private final Renderer cypherRenderer = Renderer.getDefaultRenderer();

    @Test
    void testQuery() throws Exception {
        ProblemResearchParamDto paramDto =
                ProblemResearchParamDto.of(
                        Set.of("skill_id1", "skill_id2"),
                        Year.of(2022),
                        YearResearchingType.ON_OR_AFTER,
                        Set.of(ProblemSourceType.KICE),
                        Set.of("ps_id1", "ps_id2"),
                        Set.of());
        ResultStatement query = problemResearchRepository.generateResearchQuery(paramDto);
        System.out.println(cypherRenderer.render(query));
    }

    @Test
    void test_query_2() throws Exception {
        // given
        UnitChainDto unitChainDto1 = UnitChainDto.of("head_id1", null, null);
        UnitChainDto unitChainDto2 = UnitChainDto.of("head_id2", "middle_id1", null);
        UnitChainDto unitChainDto3 = UnitChainDto.of("head_id2", "middle_id2", null);
        ProblemResearchParamDto paramDto =
                ProblemResearchParamDto.of(
                        Set.of("skill_id_1", "skill_id_2"),
                        Year.of(2022),
                        YearResearchingType.ON_OR_AFTER,
                        Set.of(),
                        Set.of(),
                        Set.of(unitChainDto1, unitChainDto2, unitChainDto3));
        // when
        Set<String> result = problemResearchRepository.research(paramDto);
        // then
        result.forEach(System.out::println);
    }

    @Test
    void execute_test() throws Exception {
        // given
        ProblemResearchParamDto paramDto =
                ProblemResearchParamDto.of(
                        Set.of(
                                "a00a51d3-8ae0-4a74-b678-b2d2b298b5fa",
                                "f175ec86-a683-410a-b773-d6c66e633d94"),
                        Year.of(2022),
                        YearResearchingType.ON_OR_AFTER,
                        Set.of(),
                        Set.of(),
                        Set.of());
        // when
        Set<String> result = problemResearchRepository.research(paramDto);
        // then
        result.forEach(System.out::println);
    }
}
