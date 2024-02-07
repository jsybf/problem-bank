package gitp.problembank.domain.tag;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("head_unit_tag")
@Getter
public class HeadUnitTag extends AbstractUnitTag {

    @Relationship(type = "unit_chain")
    @Setter
    private Set<MiddleUnitTag> middleUnitTagSet = new HashSet<>();

    public HeadUnitTag(String unitName, Integer unitNum) {
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
