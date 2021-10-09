package ar.com.ada.api.mutantes.repos;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.mutantes.entities.Mutant;

@Repository

public interface MutantRepository extends MongoRepository <Mutant, ObjectId> {

    Mutant findByUniqueHashDNA(String hash);
}
