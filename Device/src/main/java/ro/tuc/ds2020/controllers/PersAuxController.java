package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.builders.JsonDto;
import ro.tuc.ds2020.entities.PersAux;
import ro.tuc.ds2020.services.DeviceService;
import ro.tuc.ds2020.services.PersAuxService;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/pers")
public class PersAuxController {

    private final PersAuxService persAuxService;

    @Autowired
    public PersAuxController(PersAuxService persAuxService) {
        this.persAuxService = persAuxService;
    }


    @PostMapping("/insertingDB")
    public ResponseEntity<PersAux> insertInDB(@RequestBody String idString)
    {
        System.out.println("ALOOOO");
        UUID id = UUID.fromString(idString);
        PersAux persAux = persAuxService.insertPers(id);
        return new ResponseEntity<>(persAux, HttpStatus.OK);
    }
//@PostMapping("/insertingDB/{idString}")
//public ResponseEntity<PersAux> insertInDB(@PathVariable("idString") String idString)
//{
//    System.out.println("ALOOOO");
//    UUID id = UUID.fromString(idString);
//    PersAux persAux = persAuxService.insertPers(id);
//    return new ResponseEntity<>(persAux, HttpStatus.OK);
//}
    @DeleteMapping("/deletePers/{id}")
    public ResponseEntity<Void> deleteInDB(@PathVariable UUID id){
        persAuxService.deletePers(id);


        return new ResponseEntity<>(HttpStatus.OK);

    }

}
