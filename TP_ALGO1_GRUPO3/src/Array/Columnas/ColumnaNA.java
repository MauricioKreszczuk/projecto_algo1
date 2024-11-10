package Array.Columnas;

import java.util.List;
import java.util.Objects;

import Array.ArrayCelda;
import Celda.Celda;

public class ColumnaNA extends Columna<String> {

    public ColumnaNA(ArrayCelda array){
        super(array);
    }

    public ColumnaNA(){
        super("");
    }

    public ColumnaNA(String nombre) {
        super(nombre, String.class);
    }

    public ColumnaNA(String nombre, List<Celda<?>> Celdas){
        super(nombre, Celdas);
    }

    @Override
    public void agregarValor(String valor) {
        throw new UnsupportedOperationException("No se puede agregar valores concretos a una celda NA.");
    }

    public String obtenerValor(int indice) {
        return null; 
    }



    @Override
    public boolean equals(Object obj) {
        // Verifica si el objeto es la misma instancia
        if (this == obj) {
            return true;
        }
        
        // Verifica que el objeto no sea null y que sea exactamente de la misma clase
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        // Hace el cast seguro
        ColumnaNA otraColumna = (ColumnaNA) obj;
        
        // Compara el nombre y las celdas (o cualquier otro atributo relevante de la clase Columna)
        return Objects.equals(this.nombre, otraColumna.nombre) &&
            Objects.equals(this.celdas, otraColumna.celdas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, celdas);
    }


}
