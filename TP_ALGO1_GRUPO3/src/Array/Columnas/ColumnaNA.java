package Array.Columnas;

public class ColumnaNA extends Columna<String> {

    public ColumnaNA(String nombre) {
        super(nombre, String.class);
    }

    @Override
    public void agregarValor(String valor) {
        throw new UnsupportedOperationException("No se puede agregar valores concretos a una celda NA.");
    }

    public String obtenerValor(int indice) {
        return null; 
    }
}
