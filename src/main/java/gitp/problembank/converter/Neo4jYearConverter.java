package gitp.problembank.converter;

import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

/**
 * neo4j doesn't support (neo4j data type) <-> java.time.Year so implementing custom type converter
 * is required
 */
public class Neo4jYearConverter implements GenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> convertiblePairSet = new HashSet<>();
        convertiblePairSet.add(new ConvertiblePair(Value.class, Year.class));
        convertiblePairSet.add(new ConvertiblePair(Year.class, Value.class));
        return convertiblePairSet;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (Year.class.isAssignableFrom(sourceType.getType())) {
            return Values.value(((Year) source).getValue());
        } else {
            return Year.of(((Value) source).asInt());
        }
    }
}
