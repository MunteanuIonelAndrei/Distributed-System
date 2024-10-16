package monotoringSystemVersion2.monotoring.converters;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Converter
public class DoubleArrayConverter implements AttributeConverter<Double[], String> {

    @Override
    public String convertToDatabaseColumn(Double[] attribute) {
        if (attribute == null) {
            return null; // Return null if the entire array is null
        }
        // Convert array to string, handling null elements
        return Arrays.stream(attribute)
                .map(value -> value == null ? "null" : value.toString())
                .collect(Collectors.joining(","));
    }

    @Override
    public Double[] convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null; // Return null if the database value is null
        }
        // Convert string back to array, handling "null" strings
        return Arrays.stream(dbData.split(","))
                .map(this::parseDoubleSafely)
                .toArray(Double[]::new);
    }

    private Double parseDoubleSafely(String str) {
        return "null".equals(str) ? null : Double.valueOf(str);
    }
}
