package Array.Columnas;

public class test {
    public static void main(String[] args) {
        ColumnaBoolean columna = new ColumnaBoolean("Columna Booleana");
        
        // Agregar valores
        columna.agregarValor(true);
        columna.agregarValor(false);
        columna.agregarValor(true);
        System.out.println(columna.obtenerEtiquetas());
    }
}
