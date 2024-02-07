package gitp.problembank.dto.domain;

import gitp.problembank.domain.tag.AbstractUnitTag;
import gitp.problembank.domain.tag.TailUnitTag;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AbstractUnitTagDto {

    @Setter private String id;

    @Setter private String unitName;
    @Setter private Integer unitNum;

    public AbstractUnitTagDto(String id, String unitName, Integer unitNum) {
        this.id = id;
        this.unitName = unitName;
        this.unitNum = unitNum;
    }

    public static AbstractUnitTagDto toDto(AbstractUnitTag entity) {
        return new AbstractUnitTagDto(entity.getId(), entity.getUnitName(), entity.getUnitNum());
    }

    public TailUnitTag toTailUnitTag() {
        return new TailUnitTag(this.unitName, this.unitNum);
    }
}
