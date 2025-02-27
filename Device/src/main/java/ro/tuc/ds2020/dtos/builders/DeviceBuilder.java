package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.entities.Device;


public class DeviceBuilder {

    private DeviceBuilder() {
    }

    public static DeviceDTO toPersonDTO(Device person) {
        return new DeviceDTO(person.getIdentifier(), person.getName(), person.getAddress(),person.getMaxHours());
    }

    public static Device toPersonDTO(DeviceDTO person) {
        return new Device(person.getId(), person.getDescription(), person.getAddress(),person.getMaxHours());
    }

    public static DeviceDetailsDTO toPersonDetailsDTO(Device person) {
        return new DeviceDetailsDTO(person.getIdentifier(), person.getName(), person.getAddress(), person.getMaxHours());
    }

    public static Device toEntity(DeviceDetailsDTO deviceDetailsDTO) {
        return new Device(
                deviceDetailsDTO.getId(),
                deviceDetailsDTO.getName(),
                deviceDetailsDTO.getAddress(),
                deviceDetailsDTO.getMaxHours()
                );
    }
}
