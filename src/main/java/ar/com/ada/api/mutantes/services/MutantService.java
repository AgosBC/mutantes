package ar.com.ada.api.mutantes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.mutantes.entities.DNASample;
import ar.com.ada.api.mutantes.entities.Human;
import ar.com.ada.api.mutantes.entities.Mutant;
import ar.com.ada.api.mutantes.repos.HumanRepository;
import ar.com.ada.api.mutantes.repos.MutantRepository;
import ar.com.ada.api.mutantes.security.Crypto;

@Service
public class MutantService {


    @Autowired
    MutantRepository mutantRepository;

    @Autowired
    HumanRepository humanRepository;


    //match by rows buscar combinacion de 4 caracteres iguales en las filas

    private int matchsByRows(char[][] matrix){
        int matchs = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if(j + 4 > matrix.length)
                break;

                if(matrix[i][j] == matrix[i][j+1] &&
                    matrix[i][j] == matrix[i][j+2] &&
                    matrix[i][j] == matrix[i][j+3]){
                     matchs++;
                }
                
                
            }
            
        }
        return matchs;
    }

    private int matchsByColumns(char[][] matrix){
        int matchs = 0;
        //recorrer por columnas
        for (int j = 0; j < matrix.length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                
                if (i + 4 > matrix.length)
                    break;

                if (matrix[i][j] == matrix[i+1][j] &&
                    matrix[i][j] == matrix[i+2][j] &&
                    matrix[i][j] == matrix[i+3][j] )
                    matchs++;
            }
        }
        return matchs;

    }

    private int matchsByDiagonal(char[][] matrix){
        int matchs = 0;
        int matrixSize = matrix.length;

        //diagonales de izq a derecha
        for (int k = 0; k < matrix.length * 2; k++) {
            for (int j = 0; j <= k; j++) {
                int i = k - j;
                if (i < matrixSize && j < matrixSize) {
                    //Aca estoy en diagonal de izq a derecha.

                    if (i - 4 < 0 || j + 4 >= matrixSize) {
                        continue;
                    }
                    // SI A = B, y B = C, C = D, A = D?
                    // A = B, A = C, A = D y B = D?
                    if (matrix[i][j] == matrix[i - 1][j + 1] 
                        && matrix[i][j] == matrix[i - 2][j + 2]
                        && matrix[i][j] == matrix[i - 3][j + 3]) {
                        matchs++;
                    }
                }
            }

        }
        return matchs;
    }

    public boolean isMutant(String[] dna){
        //logica va aca
        DNASample sample = new DNASample(dna);

        if (!sample.isValid())
            return false;
            
        char[][] matrix = sample.toMatrix();
        char[][] mirror = sample.toMatrixMirror();
        int matchsByRow = matchsByRows(matrix);
        int matchsByColumn = matchsByColumns(matrix);
        int matchsByDiagonal1 = matchsByDiagonal(matrix);
        //espejo
        int matchsByDiagonal2 = matchsByDiagonal(mirror);

        //que la suma de secuencias encontradas, sea al menos 2
        return matchsByRow +  matchsByColumn + matchsByDiagonal1 + matchsByDiagonal2 > 1;
    }

    public Mutant registerMutant(String name, String[] dna) {
        Mutant mutant = new Mutant();
        mutant.setName(name);
        mutant.setDna(dna);

        return mutantRepository.save(mutant);


    }

    public Human registerHuman(String name, String[] dna) {
        Human human = new Human();
        human.setName(name);
        human.setDna(dna);

        return humanRepository.save(human);
    }

    public String calculateHash(String[] dna){

        StringBuilder sb = new StringBuilder(); //constructor de strings

        for (String secuence : dna) {
            sb.append(secuence + "|"); // por cada string de la secuencia, lo va a concatenar y agregar una |
            
        }
        String dnaToHash = sb.toString(); //transforma ese string buider a un string que sera hasheada en hnaHashed

        String dnaHashed = Crypto.hash(dnaToHash, "Magneto Rulz");

        return dnaHashed; //devuelve el adn hasheado, algo unico que permitira hacer la comparacion para saber si la 
                            // muestra yaexiste en la base de datos

    }

    

    

    


    
}
