package Array.Columnas;

public class ColumnaString extends Columna<String> {

    public ColumnaString(String nombre) {
        super(nombre, String.class);
    }

    @Override
    public void agregarValor(String valor) {
        validarTipo(valor); // Validar el tipo
        super.agregarValor(valor);
    }
}
