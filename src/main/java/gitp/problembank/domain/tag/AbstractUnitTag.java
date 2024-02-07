package gitp.problembank.domain.tag;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.Objects;

@Node("unit_tag")
@Getter
public abstract class AbstractUnitTag {
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;

    @Setter private final String unitName;
    @Setter private final Integer unitNum;

    public AbstractUnitTag(String unitName, Integer unitNum) {
        this.unitName = unitName;
        this.unitNum = unitNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractUnitTag that = (AbstractUnitTag) o;

        if (!Objects.equals(unitName, that.unitName)) return false;
        return Objects.equals(unitNum, that.unitNum);
    }

    @Override
    public int hashCode() {
        int result = unitName != null ? unitName.hashCode() : 0;
        result = 31 * result + (unitNum != null ? unitNum.hashCode() : 0);
        return result;
    }
}
