package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.dtos.builders.JsonDto;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.PersAux;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.PersAuxRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;
    private final PersAuxRepository persAuxRepository;
    @Autowired
    private MessageSenderService messageSenderService;



    @Autowired
    public DeviceService(DeviceRepository deviceRepository, PersAuxRepository persAuxRepository, MessageSenderService messageSenderService) {
        this.deviceRepository = deviceRepository;
        this.persAuxRepository = persAuxRepository;
        this.messageSenderService=messageSenderService;
    }

    public List<DeviceDTO> findDevice() {
        List<Device> listDevice  = deviceRepository.findAll();
        return listDevice.stream()
                .map(DeviceBuilder::toPersonDTO)
                .collect(Collectors.toList());
    }

    public DeviceDetailsDTO findDeviceByID(int id) {
        Optional<Device> prosumerOptional = deviceRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Person with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        return DeviceBuilder.toPersonDetailsDTO(prosumerOptional.get());
    }

    public int insert(DeviceDetailsDTO personDTO, UUID id) {
        Optional<PersAux> persAuxOptional = persAuxRepository.findPersAuxById(id);

        if (persAuxOptional.isPresent()) {
            PersAux persAux = persAuxOptional.get();
            Device device = DeviceBuilder.toEntity(personDTO);
            device.setBoss(persAux);

            device = deviceRepository.save(device);
            JsonDto message = new JsonDto(0,  device.getIdentifier(),id,personDTO.getMaxHours());
            messageSenderService.sendMessage(message);
            return device.getIdentifier();
        } else {
            return 0;
        }
    }


    public void update(int id, String denumire, String address, double maxHours) {
        Device device = deviceRepository.findById(id).get();
        device.setName(denumire);
        device.setAddress(address);
        device.setMaxHours(maxHours);
        JsonDto message = new JsonDto(2,id,maxHours);
        messageSenderService.sendMessage(message);
        deviceRepository.save(device);
    }

    public void delete(int id) {
        Device device = deviceRepository.findById(id).get();
        JsonDto message = new JsonDto(1,id);
        messageSenderService.sendMessage(message);
        deviceRepository.delete(device);
    }

    public List<DeviceDTO> findDevicesForPerson(UUID personId) {
        Optional<PersAux> personExists = persAuxRepository.findPersAuxById(personId);
        if (!personExists.isPresent()) {
            return new ArrayList<>();
        } else {
            PersAux personTabel =  personExists.get();
            List<Device> devicesForPerson = deviceRepository.findAllByBoss(personTabel);
            return devicesForPerson.stream()
                    .map(DeviceBuilder::toPersonDTO)
                    .collect(Collectors.toList());
        }
    }

}
