package ar.com.ada.api.mutantes.controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.ea.async.Async;

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

    static{
        Async.init();
    }//metodo de bloque estatico que se ejecuta para todos los controllers al menos una vez para inicializar el aync/await

    

    @PostMapping(value="/mutant")
    public ResponseEntity<?> postSampleASINC(@RequestBody SampleRequest sampleRequest) throws InterruptedException, ExecutionException {

        GenericResponse r = new GenericResponse();

        //PRIMERA OPCION USAR COMPARABLE FUTURE

        //aca como hay un calculo intensivo (CPU intensive) lo hacemos asincronico
        // usando un completable future(medio obsoleto, primer acercamiento en JAVA)
        // en este caso, otro se encarga de completar esa operacion y nosotros nos quedamos esperando
        CompletableFuture<Boolean> isMutantResult = CompletableFuture.supplyAsync(()-> mutantService.isMutant(sampleRequest.dna));

        //mientras que la operacion anterior (isMutantResult) no este completada (o sea, no este completado ese futuro)
        while (!isMutantResult.isDone()) {
            Thread.sleep(100); // dormir 100 milisegundo. ESTO NO SE HACE EN UN CONTROLLER, SOLO EN APLICACION DE CONSOLA
                                // SERVICIOS, ETC
            
        }
        // el get me develve si ese futuro termino siendo true o no 
        if(isMutantResult.get()){

            //SEGUNDA OPCION UTILIZANDO LA CLASE FUTURE
            Future<Mutant> mutantRegResult = mutantService.registerMutantAsync(sampleRequest.name, sampleRequest.dna);
            
            while (!mutantRegResult.isDone()) {
                Thread.sleep(100);
                
            }            
            
            r.isOk = true;
            r.id = mutantRegResult.get().get_id();
            r.message = "it's a mutant";
        }
        else { // OPCOON 3 UTILIZANDO LA NOTACION DE EA (ELECTRONIC ARTS) DE LA DEPENDENCIA EA.ASYNC (no anda con Future, si con completable future)
           CompletableFuture<Human> humanRegResult = CompletableFuture.supplyAsync(()-> 
                                                        mutantService.registerHuman(sampleRequest.name, sampleRequest.dna));

           Human human = Async.await(humanRegResult); //en vez de mandar a dormir, llamo al metodo esperar (await)
            r.isOk = true;
            r.id = human.get_id();
            r.message = "it's a human";

        }
        
        return ResponseEntity.ok(r);
    }

    /** como hacer metodos asyn en c# .net
     * crear metodo async : public async Task<Human> registerHumanAsync(String name, String[] dna){bloque de codigo}
     * al llamarlo , de la sig manera:
     * Human human = await registerHumanAsync("wolverine", dnaSample)
     * aqui se usa la palabra clave await
     */

     /** todo esto es de manera asincronica, comienza con un thread pero puede terminar por cualquier otro, no hay que 
      * implementar threads, extends, etc. se usa el framework para que se encargue. cuando se encuentra un await asyn, 
      * significa que el metodo es asincronico
      */

      /** las variables atomicas son variables que se ejecutaran automaticamente de forma concurrente, no tiene que
       * ver con un tema asincronico si no con un tema de sincronismo. la variable atomica no deja que dos thread la 
       * modifiquen al mismo tiempo. si no que cuando le hago una modificacion va a bloquear, va a dejar que se la modifique
       * va a hacer que esperen las demas modificaciones hasta que termine
       */
    
   
    
}
