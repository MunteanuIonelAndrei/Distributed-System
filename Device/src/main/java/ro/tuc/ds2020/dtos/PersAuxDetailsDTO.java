package ro.tuc.ds2020.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersAuxDetailsDTO {
    private UUID id;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersAuxDetailsDTO that = (PersAuxDetailsDTO) o;
        return id == that.id;
    }

}
