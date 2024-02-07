package gitp.problembank.dto;

import gitp.problembank.dto.domain.AbstractUnitTagDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitChainDto {
    private AbstractUnitTagDto headUnit;
    private AbstractUnitTagDto middleUnit;
    private AbstractUnitTagDto tailUnit;

    public UnitChainDto(
            String headUnitName,
            Integer headUnitNum,
            String headUnitId,
            String middleUnitName,
            Integer middleUnitNum,
            String middleUnitId,
            String tailUnitName,
            Integer tailUnitNum,
            String tailUnitId) {
        this.headUnit = new AbstractUnitTagDto(headUnitId, headUnitName, headUnitNum);
        this.middleUnit = new AbstractUnitTagDto(middleUnitId, middleUnitName, middleUnitNum);
        this.tailUnit = new AbstractUnitTagDto(tailUnitId, tailUnitName, tailUnitNum);
    }
}
