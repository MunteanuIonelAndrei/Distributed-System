package monotoringSystemVersion2.monotoring.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import monotoringSystemVersion2.monotoring.converters.DoubleArrayConverter;
import org.springframework.stereotype.Service;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class ManagementAllDevices {

    @Id
   // @GeneratedValue(strategy = GenerationType.AUTO) // If you want automatic UUID generation
    private int deviceId;
    private UUID personId;
    private Date timestamp;
    private double meanValue;
    private double maxHours;

    @Convert(converter = DoubleArrayConverter.class)
    private Double[] doubleValues;



    // Other fields can be added here as needed
}
