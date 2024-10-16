package ro.tuc.ds2020.dtos.builders;
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

public class JsonDto {
      private int typeOfMessage;//0-insert, 1-delete,2-update,3-delete person
      private int deviceId;
      private UUID personId;
      private double maxHours;

public JsonDto(int typeOfMessage,int deviceId, double value){
    this.typeOfMessage=typeOfMessage;
    this.deviceId=deviceId;
    this.maxHours=value;
}
    public JsonDto(int typeOfMessage, int deviceId){
        this.typeOfMessage=typeOfMessage;
        this.deviceId=deviceId;

    }

    public JsonDto(int typeOfMessage, UUID personId){
        this.typeOfMessage=typeOfMessage;
        this.personId=personId;

    }

}
