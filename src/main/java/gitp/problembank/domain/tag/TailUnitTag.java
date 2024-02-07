package gitp.problembank.domain.tag;

import lombok.Getter;

import org.springframework.data.neo4j.core.schema.Node;

@Node("tail_unit_tag")
@Getter
public class TailUnitTag extends AbstractUnitTag {

    public TailUnitTag(String unitName, Integer unitNum) {
        super(unitName, unitNum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractUnitTag that = (AbstractUnitTag) o;
        return super.equals(that);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
