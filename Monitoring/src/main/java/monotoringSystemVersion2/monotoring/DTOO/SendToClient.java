package monotoringSystemVersion2.monotoring.DTOO;

import lombok.Data;
import monotoringSystemVersion2.monotoring.converters.DoubleArrayConverter;

import javax.persistence.Convert;
import java.util.Date;
@Data
public class SendToClient {
    private int deviceId;
    private Date timestamp;
    private double meanValue;
    private double maxHours;
    @Convert(converter = DoubleArrayConverter.class)
    private Double[] doubleValues;
}
