package ar.com.ada.api.mutantes.entities;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "humans")
public class Human {

    private ObjectId _id;
    private String name;
    private String[] dna;
    private String uniqueHashDNA;
    
    public String get_id() {
        return _id.toHexString();
    }
    public void set_id(String _id) {
        this._id = new ObjectId(_id);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String[] getDna() {
        return dna;
    }
    public void setDna(String[] dna) {
        this.dna = dna;
    }

    public String getUniqueHashDNA() {
        return uniqueHashDNA;
    }
    public void setUniqueHashDNA(String uniqueHashDNA) {
        this.uniqueHashDNA = uniqueHashDNA;
    }

    
    
}
