package ar.com.ada.api.mutantes.services;

import org.springframework.stereotype.Service;

import ar.com.ada.api.mutantes.entities.DNASample;

@Service
public class MutantService {
    
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

    


    
}
