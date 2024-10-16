package ro.tuc.ds2020.dtos.builders;

import lombok.NoArgsConstructor;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.dtos.PersAuxDTO;
import ro.tuc.ds2020.dtos.PersAuxDetailsDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.PersAux;

@NoArgsConstructor

public class PersAuxBuilder {

    public static PersAuxDTO toPersonDTO(PersAux person) {
        return new PersAuxDTO(person.getId());
    }

    public static PersAuxDetailsDTO toPersonDetailsDTO(PersAux person) {
        return new PersAuxDetailsDTO(person.getId());
    }

    public static PersAux toPerson(PersAuxDTO person) {
        return new PersAux(person.getId());
    }

    public static PersAux toEntity(PersAuxDetailsDTO persAuxDetailsDTO) {
        return new PersAux(
                persAuxDetailsDTO.getId()
        );
    }
}
