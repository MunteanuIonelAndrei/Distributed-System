package ro.tuc.ds2020.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DeviceDTO extends RepresentationModel<DeviceDTO> {
    private int id;
    private String Description;
    private String address;
    private double maxHours;



    public DeviceDTO(String Description, String address, int maxHours)
    {
        this.Description=Description;
        this.address=address;
        this.maxHours=maxHours;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDTO deviceDTO = (DeviceDTO) o;
        return maxHours == deviceDTO.maxHours &&
                Objects.equals(Description, deviceDTO.Description) &&
                Objects.equals(address, deviceDTO.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Description, maxHours);
    }
}
