package gitp.problembank.repository;

import gitp.problembank.domain.tag.HeadUnitTag;
import gitp.problembank.domain.tag.MiddleUnitTag;
import gitp.problembank.domain.tag.TailUnitTag;
import gitp.problembank.domain.tag.YearTag;
import gitp.problembank.dto.UnitChainDto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jTemplate;

@SpringBootTest
class UnitTagCustomRepositoryImplTest {
    @Autowired UnitTagCustomRepositoryImpl unitTagCustomRepository;
    @Autowired Neo4jTemplate neo4jTemplate;

    @Test
    void saveMiddleNodeTest() throws Exception {
        // given
        HeadUnitTag head1 = new HeadUnitTag("head_1", 1);
        MiddleUnitTag middle1 = new MiddleUnitTag("middle_1", 1);
        // when
        unitTagCustomRepository.saveHead(head1);

        MiddleUnitTag foundMiddleUnitTag =
                neo4jTemplate.findById(middle1.getId(), MiddleUnitTag.class).orElseThrow();

        // then
        System.out.println();
    }

    @Test
    void deleteTest() throws Exception {
        // given
        HeadUnitTag head1 = new HeadUnitTag("head_1", 1);
        MiddleUnitTag middle1 = new MiddleUnitTag("middle_1", 1);
        head1.getMiddleUnitTagSet().add(middle1);
        unitTagCustomRepository.saveHead(head1);
        // when
        unitTagCustomRepository.delete(head1.getId());
        // then
    }

    @Test
    void getUnitChainByTailUnitTagId_test() throws Exception {
        // given
        HeadUnitTag head = new HeadUnitTag("head_1", 1);
        MiddleUnitTag middle = new MiddleUnitTag("middle_1", 1);
        TailUnitTag tail = new TailUnitTag("tail_1", 1);
        middle.getTailUnitTagSet().add(tail);
        head.getMiddleUnitTagSet().add(middle);
        unitTagCustomRepository.saveHead(head);
        // when
        UnitChainDto unitChain =
                unitTagCustomRepository.getUnitChainDtoByTailUnitTagId(tail.getId()).orElseThrow();

        // then

        Assertions.assertThat(unitChain.getTailUnit().getUnitNum()).isEqualTo(tail.getUnitNum());
        Assertions.assertThat(unitChain.getMiddleUnit().getUnitName()).isEqualTo(middle.getUnitName());
    }
}
