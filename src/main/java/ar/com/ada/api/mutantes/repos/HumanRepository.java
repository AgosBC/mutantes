package ar.com.ada.api.mutantes.repos;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.mutantes.entities.Human;
@Repository
public interface HumanRepository extends MongoRepository<Human, ObjectId>{

    Human findByUniqueHashDNA(String hash);
    
}
