package gitp.problembank.repository;

import gitp.problembank.domain.tag.HeadUnitTag;
import gitp.problembank.domain.tag.MiddleUnitTag;
import gitp.problembank.domain.tag.TailUnitTag;
import gitp.problembank.dto.UnitChainDto;
import java.util.Optional;

public interface UnitTagCustomRepository {
    Optional<UnitChainDto> getUnitChainDtoByTailUnitTagId(String tailNodeId);

    void saveHead(HeadUnitTag headUnitTag);

    void saveMiddle(MiddleUnitTag middleUnitTag);

    void saveTail(TailUnitTag tailUnitTag);

    void delete(String id);

    Optional<HeadUnitTag> findHeadById(String id);

    Optional<MiddleUnitTag> findMiddleById(String id);

    Optional<TailUnitTag> findTailById(String id);
}
