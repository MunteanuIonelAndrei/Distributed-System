package monotoringSystemVersion2.monotoring.DTOO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Service
public class JsonDto {
    private int deviceId;
    private Date timestamp;
    //   private UUID deviceId=null;
    private double value;
/*public JsonDto(double value){
    this.value=value;
}*/
}
