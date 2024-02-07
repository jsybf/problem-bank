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
     * save unitChain
     * if unit in unitChain is already persisted then add subordinate unit to this unit and save
     * if unit in unitChain is not already persisted then create new unit
     * @param unitChainDto
     */
    public void saveUnitChain(UnitChainDto unitChainDto) {
        if (unitChainDto.getHeadUnitId() == null) {
            HeadUnitTag headUnitTag =
                    new HeadUnitTag(unitChainDto.getHeadUnitName(), unitChainDto.getHeadUnitNum());
            unitTagCustomRepository.saveHead(headUnitTag);
            unitChainDto.setHeadUnitId(headUnitTag.getId());
        }
        if (unitChainDto.getMiddleUnitId() == null) {
            MiddleUnitTag middleUnitTag =
                    new MiddleUnitTag(
                            unitChainDto.getMiddleUnitName(), unitChainDto.getMiddleUnitNum());
            HeadUnitTag headUnitTag =
                    unitTagCustomRepository
                            .findHeadById(unitChainDto.getHeadUnitId())
                            .orElseThrow();
            headUnitTag.getMiddleUnitTagSet().add(middleUnitTag);
            unitTagCustomRepository.saveHead(headUnitTag);
            unitChainDto.setMiddleUnitId(middleUnitTag.getId());
        }
        if (unitChainDto.getTailUnitId() == null) {
            TailUnitTag tailUnitTag = new TailUnitTag(unitChainDto.getTailUnitName(), unitChainDto.getTailUnitNum());
            MiddleUnitTag middleUnitTag = unitTagCustomRepository.findMiddleById(unitChainDto.getMiddleUnitId())
                .orElseThrow();
            middleUnitTag.getTailUnitTagSet().add(tailUnitTag);
            unitTagCustomRepository.saveMiddle(middleUnitTag);
            unitChainDto.setTailUnitId(tailUnitTag.getId());
        }
    }

    public void updateUnitContentById(String id) {
        
    }
}
