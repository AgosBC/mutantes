package ar.com.ada.api.mutantes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.mutantes.entities.Human;
import ar.com.ada.api.mutantes.entities.Mutant;
import ar.com.ada.api.mutantes.models.request.SampleRequest;
import ar.com.ada.api.mutantes.models.response.GenericResponse;
import ar.com.ada.api.mutantes.services.MutantService;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class MutantController {

    @Autowired
    MutantService mutantService;

    

    @PostMapping(value="/mutant")
    //TODO: process POST request
    public ResponseEntity<?> postMethodName(@RequestBody SampleRequest sampleRequest) {

        GenericResponse r = new GenericResponse();

        if(mutantService.isMutant(sampleRequest.dna)){
            Mutant mutant = mutantService.registerMutant(sampleRequest.name, sampleRequest.dna);
            r.isOk = true;
            r.id = mutant.get_id();
            r.message = "it's a mutant";
        }
        else {
           Human human = mutantService.registerHuman(sampleRequest.name, sampleRequest.dna);

            r.isOk = true;
            r.id = human.get_id();
            r.message = "it's a human";

        }
        
        return ResponseEntity.ok(r);
    }
    
    
}
