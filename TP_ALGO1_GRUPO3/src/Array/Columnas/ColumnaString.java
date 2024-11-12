package Array.Columnas;

import java.util.List;
import java.util.Objects;

import Array.ArrayCelda;
import Celda.Celda;

public class ColumnaString extends Columna<String> {

    public ColumnaString(ArrayCelda array){
        super(array);
    }

    public ColumnaString(String nombre) {
        super(nombre, String.class);
    }
    
    public ColumnaString(String nombre, List<Celda<?>> Celdas) {
        super(nombre, Celdas);
    }
    

    @Override
    public void agregarValor(String valor) {
        validarTipo(valor); 
        super.agregarValor(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        ColumnaString otraColumna = (ColumnaString) obj;
        return Objects.equals(this.nombre, otraColumna.nombre) &&
            Objects.equals(this.celdas, otraColumna.celdas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, celdas);
    }

}
