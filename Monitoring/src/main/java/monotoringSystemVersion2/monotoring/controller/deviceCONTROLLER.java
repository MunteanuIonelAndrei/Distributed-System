package monotoringSystemVersion2.monotoring.controller;

import monotoringSystemVersion2.monotoring.DTOO.MessageListenerClass;
import monotoringSystemVersion2.monotoring.DTOO.SendToClient;
import monotoringSystemVersion2.monotoring.Service.ManageDB;
import monotoringSystemVersion2.monotoring.entity.ManagementAllDevices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/manage")
public class deviceCONTROLLER {
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;

    private ManageDB manageDB;
    private MessageListenerClass sendClient;

    @Autowired
    public deviceCONTROLLER(ManageDB manageDB)
    {
        this.manageDB = manageDB;
    }

    @GetMapping(value = "/findAll/{id}")
    public ResponseEntity<Double[]> getPersonDevices(@PathVariable("id") int deviceDTO) {
        Double[] dto = manageDB.getValues(deviceDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/getData")
    public ResponseEntity<SendToClient> getPersonDevices() {
        SendToClient dto = sendClient.getSendToClient();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @MessageMapping("/changes")
    @SendTo("/topic/updates")
    public List<ManagementAllDevices> notifyChanges(){
        return manageDB.findAll();
    }
    // Method to call when an update occurs in the database
    public void databaseUpdated() {
        List<ManagementAllDevices> updatedProducts = manageDB.findAll();
//        messagingTemplate.convertAndSend("/topic/updates", updatedProducts);
    }
}
