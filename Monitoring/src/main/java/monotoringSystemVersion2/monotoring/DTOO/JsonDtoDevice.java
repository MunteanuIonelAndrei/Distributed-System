package monotoringSystemVersion2.monotoring.DTOO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Service
public class JsonDtoDevice {
    private int typeOfMessage;//0-insert, 1-delete,2-update
    private int deviceId;
    private UUID personId;
    private double maxHours;
    public JsonDtoDevice(int typeOfMessage, double value){
        this.typeOfMessage=typeOfMessage;
        this.maxHours=value;
    }
    public JsonDtoDevice(int typeOfMessage, int deviceId){
        this.typeOfMessage=typeOfMessage;
        this.deviceId=deviceId;

    }

}
