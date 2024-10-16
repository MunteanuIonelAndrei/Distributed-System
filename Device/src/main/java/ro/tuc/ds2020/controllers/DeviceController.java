package ro.tuc.ds2020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.dtos.builders.JsonDto;
import ro.tuc.ds2020.services.DeviceService;
import ro.tuc.ds2020.services.MessageSenderService;
import ro.tuc.ds2020.services.PersAuxService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/device")
public class DeviceController {

    private final DeviceService deviceService;
    private final PersAuxService persAuxService;

  //  private final MessageSenderService messageSenderService;
    @Autowired
    public DeviceController(DeviceService deviceService, PersAuxService persAuxService, MessageSenderService messageSenderService) {
        this.deviceService = deviceService;
        this.persAuxService = persAuxService;
     //   this.messageSenderService = messageSenderService;
    }

    @GetMapping()
    public ResponseEntity<List<DeviceDTO>> getPersons() {
        List<DeviceDTO> dtos = deviceService.findDevice();
        for (DeviceDTO dto : dtos) {
            Link personLink = linkTo(methodOn(DeviceController.class)
                    .getPerson(dto.getId())).withRel("personDetails");
            dto.add(personLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping(value = "/admin/{id}")
    public ResponseEntity<Integer> insertDevice(@Valid @RequestBody DeviceDetailsDTO deviceDTO, @PathVariable("id") UUID uuid) {
        int deviceID = deviceService.insert(deviceDTO, uuid);
       // JsonDto message = new JsonDto(1, deviceID,uuid,deviceDTO.getMaxHours());
       // messageSenderService.sendMessage(message);
        return new ResponseEntity<>(deviceID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DeviceDetailsDTO> getPerson(@PathVariable("id") int deviceID) {
        DeviceDetailsDTO dto = deviceService.findDeviceByID(deviceID);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @PostMapping(value="/admin/{id}/{denumire}/{address}/{maxHours}")
    public ResponseEntity<Map<String, String>> updateDevice(@PathVariable("id") int id,
                                                            @PathVariable("denumire") String denumire,
                                                            @PathVariable("address") String address,
                                                            @PathVariable("maxHours") int maxHours) {
        deviceService.update(id, denumire, address, maxHours);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Device updated successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value ="/admin/{id}")
    public ResponseEntity<Map<String, String>> deleteDevice(@PathVariable("id") int id)
    {
        deviceService.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Device deleted successfully");

        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @GetMapping(value = "/user/findAll/{id}")
    public ResponseEntity<List<DeviceDTO>> getPersonDevices(@PathVariable("id") UUID deviceDTO) {
        List<DeviceDTO> dto = deviceService.findDevicesForPerson(deviceDTO);
        for (DeviceDTO device: dto
             ) {
            System.out.println(device);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/findAll")
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> dto = deviceService.findDevice();
        for (DeviceDTO device: dto
        ) {
            System.out.println(device);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //TODO: UPDATE, DELETE per resourc

}
