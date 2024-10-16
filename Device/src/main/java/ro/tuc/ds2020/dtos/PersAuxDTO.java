package ro.tuc.ds2020.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PersAuxDTO {
    private UUID id;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersAuxDTO persAuxDTO = (PersAuxDTO) o;
        return id == persAuxDTO.getId();
    }

}
