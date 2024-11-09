package Array.Columnas;

import java.util.List;

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
        validarTipo(valor); // Validar el tipo
        super.agregarValor(valor);
    }
}
