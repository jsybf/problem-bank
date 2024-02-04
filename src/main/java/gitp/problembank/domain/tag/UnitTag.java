package gitp.problembank.domain.tag;

import lombok.Getter;

import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("unit_tag")
@Getter
public class UnitTag {
    @Id @GeneratedValue private String id;

    @Setter private UnitTagType type;

    @Setter private String unitName;

    private UnitTag upperTag;

    private UnitTag belowTag;

    private UnitTag(UnitTagType type, String unitName) {
        this.type = type;
        this.unitName = unitName;
    }

    public static UnitTag of(UnitTagType type, String unitName) {
        if (type == null) {
            throw new IllegalArgumentException("type shouldn't be null");
        }

        return new UnitTag(type, unitName);
    }

    public static UnitTag of(
            UnitTagType type, String unitName, UnitTag upperTag, UnitTag belowTag) {
        UnitTag unitTag = new UnitTag(type, unitName);

        unitTag.setValidBelowTag(belowTag);
        unitTag.setValidUpperTag(upperTag);

        return unitTag;
    }

    public void setValidUpperTag(UnitTag upperTag) {
        if (upperTag != null) {
            if (upperTag.getType().depth >= this.type.depth) {
                String exceptionMessage =
                        String.format(
                                "depth of upper tag(%d) should be lower than depth of new tag(%d)",
                                upperTag.getType().depth, this.type.depth);
                throw new IllegalArgumentException(exceptionMessage);
            }
        }
        this.upperTag = upperTag;
    }

    public void setValidBelowTag(UnitTag belowTag) {
        if (belowTag != null) {
            if (type.depth >= belowTag.getType().depth) {
                String exceptionMessage =
                        String.format(
                                "depth of below tag(%d) should be bigger than depth of new tag(%d)",
                                belowTag.getType().depth, type.depth);
                throw new IllegalArgumentException(exceptionMessage);
            }
        }
        this.belowTag = belowTag;
    }
}
