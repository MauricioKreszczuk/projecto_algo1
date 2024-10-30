package Array.Columnas;

public class ColumnaBoolean extends Columna<Boolean> {

    public ColumnaBoolean(String nombre) {
        super(nombre, Boolean.class);
    }

    @Override
    public void agregarValor(Boolean valor) {
        validarTipo(valor); // Validar el tipo
        super.agregarValor(valor);
    }
}
