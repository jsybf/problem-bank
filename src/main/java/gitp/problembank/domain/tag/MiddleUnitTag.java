package gitp.problembank.domain.tag;

import java.util.HashSet;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.neo4j.core.schema.Node;

import java.util.Set;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("middle_unit_tag")
@Getter
public class MiddleUnitTag extends AbstractUnitTag {

    @Relationship(type = "unit_chain")
    @Setter
    private Set<TailUnitTag> tailUnitTagSet = new HashSet<>();

    public MiddleUnitTag(String unitName, Integer unitNum) {
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
