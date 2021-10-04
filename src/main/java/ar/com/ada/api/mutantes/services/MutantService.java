package ar.com.ada.api.mutantes.services;

import org.springframework.stereotype.Service;

import ar.com.ada.api.mutantes.entities.DNASample;

@Service
public class MutantService {
    
    //match by rows buscar combinacion de 4 caracteres iguales en las filas

    private int matchByRows(char[][] matrix){
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


    
}
