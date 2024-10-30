package Array;

public class Fila extends ArrayCelda {

    public Fila(String nombre) {
        super(nombre);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombre).append(" | "); // Agrega el nombre de la fila seguido de un separador
        
        for (int i = 0; i < obtenerTamaño(); i++) {
            sb.append(obtenerValor(i) != null ? obtenerValor(i).toString() : "null").append(" | ");
        }
        
        // Eliminar el último separador " | " si hay elementos en la fila
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 3); // Elimina " | " del final
        }
    
        sb.append(" |"); // Agrega el separador final
        return sb.toString(); // Devuelve el resultado
    }
    
    
    
}
