package Array.Columnas;

import java.util.List;

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
}
