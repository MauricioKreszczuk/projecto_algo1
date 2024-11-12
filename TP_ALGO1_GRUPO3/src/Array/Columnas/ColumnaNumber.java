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
        
        ColumnaNumber otraColumna = (ColumnaNumber) obj;
        return Objects.equals(this.nombre, otraColumna.nombre) &&
            Objects.equals(this.celdas, otraColumna.celdas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, celdas);
    }
}
