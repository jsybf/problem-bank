package gitp.problembank.service;

import gitp.problembank.domain.tag.HeadUnitTag;
import gitp.problembank.domain.tag.MiddleUnitTag;
import gitp.problembank.domain.tag.TailUnitTag;
import gitp.problembank.dto.UnitChainDto;
import gitp.problembank.repository.UnitTagCustomRepositoryImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UnitTagService {
    private final UnitTagCustomRepositoryImpl unitTagCustomRepository;

    public Optional<UnitChainDto> getUnitChainDtoByTailUnitTagId(String tailId) {
        return unitTagCustomRepository.getUnitChainDtoByTailUnitTagId(tailId);
    }

    /**
     * save unitChain if unit in unitChain is already persisted then add subordinate unit to this
     * unit and save if unit in unitChain is not already persisted then create new unit
     *
     * @param unitChainDto
     */
    public void saveUnitChain(UnitChainDto unitChainDto) {
        if (unitChainDto.getHeadUnit().getId() == null) {
            HeadUnitTag headUnitTag =
                    new HeadUnitTag(
                            unitChainDto.getHeadUnit().getUnitName(),
                            unitChainDto.getHeadUnit().getUnitNum());
            unitTagCustomRepository.saveHead(headUnitTag);
            unitChainDto.getHeadUnit().setId(headUnitTag.getId());
        }
        if (unitChainDto.getMiddleUnit().getId() == null) {
            MiddleUnitTag middleUnitTag =
                    new MiddleUnitTag(
                            unitChainDto.getMiddleUnit().getUnitName(),
                            unitChainDto.getMiddleUnit().getUnitNum());
            HeadUnitTag headUnitTag =
                    unitTagCustomRepository
                            .findHeadById(unitChainDto.getHeadUnit().getId())
                            .orElseThrow();
            headUnitTag.getMiddleUnitTagSet().add(middleUnitTag);
            unitTagCustomRepository.saveHead(headUnitTag);
            unitChainDto.getMiddleUnit().setId(middleUnitTag.getId());
        }
        if (unitChainDto.getTailUnit().getId() == null) {
            TailUnitTag tailUnitTag =
                    new TailUnitTag(
                            unitChainDto.getTailUnit().getUnitName(),
                            unitChainDto.getMiddleUnit().getUnitNum());
            MiddleUnitTag middleUnitTag =
                    unitTagCustomRepository
                            .findMiddleById(unitChainDto.getMiddleUnit().getId())
                            .orElseThrow();
            middleUnitTag.getTailUnitTagSet().add(tailUnitTag);
            unitTagCustomRepository.saveMiddle(middleUnitTag);
            unitChainDto.getTailUnit().setId(tailUnitTag.getId());
        }
    }

    public void updateUnitContentById(String id) {}
}
