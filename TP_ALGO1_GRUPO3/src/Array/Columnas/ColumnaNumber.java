package Array.Columnas;

public class ColumnaNumber extends Columna<Number> {

    public ColumnaNumber(String nombre) {
        super(nombre, Number.class);
    }

    @Override
    public void agregarValor(Number valor) {
        validarTipo(valor); // Validar el tipo
        super.agregarValor(valor);
    }
}
