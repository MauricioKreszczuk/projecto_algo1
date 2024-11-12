package Array.Columnas;

import java.util.List;
import java.util.Objects;

import Array.ArrayCelda;
import Celda.Celda;

public class ColumnaBoolean extends Columna<Boolean> {

    public ColumnaBoolean(ArrayCelda array){
        super(array);
    }

    public ColumnaBoolean(String nombre) {
        super(nombre, Boolean.class);
    }

    public ColumnaBoolean(String nombre, List<Celda<?>> Celdas) {
        super(nombre, Celdas);
    }

    @Override
    public void agregarValor(Boolean valor) {
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
        
        ColumnaBoolean otraColumna = (ColumnaBoolean) obj;
        return Objects.equals(this.nombre, otraColumna.nombre) &&
               Objects.equals(this.celdas, otraColumna.celdas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, celdas);
    }


}
