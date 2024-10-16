package ro.tuc.ds2020.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.tuc.ds2020.dtos.validators.annotation.AgeLimit;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class DeviceDetailsDTO {

    private int id;
    @NotNull
    private String name;
    @NotNull
    private String address;
    private double maxHours;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDetailsDTO that = (DeviceDetailsDTO) o;
        return maxHours == that.maxHours &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, maxHours);
    }
}
