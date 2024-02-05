package gitp.problembank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitChainDto {
    private String headUnitName;
    private Integer headUnitNum;

    private String middleUnitName;
    private Integer middleUnitNum;

    private String tailUnitName;
    private Integer tailUnitNum;

    public UnitChainDto(
            String headUnitName,
            Integer headUnitNum,
            String middleUnitName,
            Integer middleUnitNum,
            String tailUnitName,
            Integer tailUnitNum) {
        this.headUnitName = headUnitName;
        this.headUnitNum = headUnitNum;
        this.middleUnitName = middleUnitName;
        this.middleUnitNum = middleUnitNum;
        this.tailUnitName = tailUnitName;
        this.tailUnitNum = tailUnitNum;
    }
}
