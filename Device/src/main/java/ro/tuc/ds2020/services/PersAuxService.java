package ro.tuc.ds2020.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.PersAuxController;
import ro.tuc.ds2020.dtos.PersAuxDTO;
import ro.tuc.ds2020.dtos.builders.JsonDto;
import ro.tuc.ds2020.dtos.builders.PersAuxBuilder;
import ro.tuc.ds2020.entities.PersAux;
import ro.tuc.ds2020.repositories.PersAuxRepository;

import java.util.UUID;

@Service
public class PersAuxService {
    private final PersAuxRepository persAuxRepository;
    @Autowired
    private MessageSenderService messageSenderService;

    @Autowired
    public PersAuxService(PersAuxRepository persAuxRepository) {
        this.persAuxRepository = persAuxRepository;
    }
    public PersAux findById(UUID id){
        return this.persAuxRepository.findById(id).get();
    }

    public PersAux insertPers(UUID id)
    {
        PersAux persAux = new PersAux(id);
        persAuxRepository.save(persAux);
        return persAux;
    }
    public void deletePers(UUID id)
    {
        PersAux persAux = persAuxRepository.findById(id).get();
        persAuxRepository.delete(persAux);
        JsonDto message = new JsonDto(3, id);
        messageSenderService.sendMessage(message);
    }
    public PersAux convertToPersAux(PersAuxDTO persAuxDTO)
    {
        return PersAuxBuilder.toPerson(persAuxDTO);
    }


}
