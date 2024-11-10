package Array.Columnas;

import java.util.List;
import java.util.Objects;

import Array.ArrayCelda;
import Celda.Celda;

public class ColumnaNumber extends Columna<Number> {

    public ColumnaNumber(ArrayCelda array){
        super(array);
    }

    public ColumnaNumber(String nombre) {
        super(nombre, Number.class);
    }
    
    public ColumnaNumber(String nombre, List<Celda<?>> Celdas) {
        super(nombre, Celdas);
    }

    @Override
    public void agregarValor(Number valor) {
        validarTipo(valor); // Validar el tipo
        super.agregarValor(valor);
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
        ColumnaNumber otraColumna = (ColumnaNumber) obj;
        
        // Compara el nombre y las celdas (o cualquier otro atributo relevante de la clase Columna)
        return Objects.equals(this.nombre, otraColumna.nombre) &&
            Objects.equals(this.celdas, otraColumna.celdas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, celdas);
    }
}
