package ar.com.ada.api.mutantes.entities;

public class DNASample {

    private String[] sequence;

    public DNASample(String[] sequence) {
        this.sequence = sequence;
    }

    // 1 hacer matiz
    public char[][] toMatrix() {
        char[][] matrix = new char[sequence.length][sequence.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = this.sequence[i].charAt(j);

            }
        }

        return matrix;

    }

    // validaciones
    // madidas NxN
    public boolean isNxN() {
        int rows = sequence.length;
        for (int i = 0; i < sequence.length; i++) {
            if (sequence[i].length() != rows)
                return false;
        }
        if (rows > 0)
            return true;

        return false;

    }

    // validar que no sea menor a 4x4
    public boolean isAtLeast4x4(){
        return isNxN() && sequence.length >=4;
    }

    public boolean hasValidDimensions(){
        return isAtLeast4x4();
    }

    // que acepte letras  A C G T
    public boolean onlyDNALetters(){
        char[][] matrix = this.toMatrix();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) { //recorrido por celda matriz [i][j]
                char character = matrix[i][j];
                if(character != 'A' && character != 'C' && character != 'G' && character != 'T')
                    return false;                
            }            
        }
        return true;
    }



    public boolean isValid(){
        boolean dimensionsOk = hasValidDimensions();
        if (!dimensionsOk)
            return false;
        if (!onlyDNALetters())
            return false; 
    
        return true;
    }

}
