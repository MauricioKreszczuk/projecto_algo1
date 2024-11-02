import Array.Columnas.ColumnaNumber;
import Array.Columnas.ColumnaString;

public class TestTabla {
    public static void main(String[] args) {
        // Crear una instancia de tu clase Tabla
        Tabla tabla = new Tabla();

        
        // Agregar columnas a la tabla
        tabla.agregarColumna(new ColumnaString("Nombre"));
        tabla.agregarColumna(new ColumnaNumber("Edad"));
        tabla.agregarColumna(new ColumnaString("Ciudad"));

        // Crear una matriz de datos
        Object[][] matriz = {
            {"Juan", 30, "Madrid"},
            {"Ana", null, "Barcelona"},
            {null, 25, "Valencia"},
            {"Luis", 28, null}
        };

        // Cargar datos desde la matriz
        //tabla.cargarDesdeMatriz(matriz);

        // Imprimir la tabla, limitando a un número específico de filas y columnas
        int limiteFilas = 5;  // Por ejemplo, imprimir las primeras 5 filas
        int limiteColumnas = 3; // Imprimir las primeras 3 columnas
        System.out.println(tabla);
    }
}
