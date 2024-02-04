package gitp.problembank.domain.tag;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UnitTagTest {
    @Test
    void incorrectDepthTest() throws Exception {
        // given
        UnitTag big = UnitTag.of(UnitTagType.BIG, "big");
        UnitTag medium = UnitTag.of(UnitTagType.MEDIUM, "medium");
        UnitTag small = UnitTag.of(UnitTagType.SMALL, "small");
        // when
        Assertions.assertThatThrownBy(() -> big.setValidUpperTag(medium))
                .isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> small.setValidBelowTag(medium))
                .isInstanceOf(IllegalArgumentException.class);
        // then
    }
}
