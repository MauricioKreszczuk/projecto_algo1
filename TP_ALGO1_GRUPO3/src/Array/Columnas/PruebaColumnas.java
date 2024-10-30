package Array.Columnas;

public class PruebaColumnas {
    public static void main(String[] args) {
        pruebaColumnaBoolean();
        pruebaColumnaNumber();
        pruebaColumnaString();
        pruebaColumnaNA();
    }

    public static void pruebaColumnaBoolean() {
        ColumnaBoolean columna = new ColumnaBoolean("Columna Booleana");
        
        // Agregar valores
        columna.agregarValor(true);
        columna.agregarValor(false);
        columna.agregarValor(true);
        
        // Imprimir valores
        System.out.println("Valores en " + columna.obtenerNombre() + ":");
        for (int i = 0; i < columna.obtenerTamaño(); i++) {
            System.out.println(columna.obtenerValor(i) != null ? columna.obtenerValor(i) : "NA");
        }

        // Intercambiar celdas
        columna.intercambiarCeldas(0, 1);
        System.out.println("Después de intercambiar:");
        for (int i = 0; i < columna.obtenerTamaño(); i++) {
            System.out.println(columna.obtenerValor(i) != null ? columna.obtenerValor(i) : "NA");
        }
    }

    public static void pruebaColumnaNumber() {
        ColumnaNumber columna = new ColumnaNumber("Columna Numérica");

        // Agregar valores
        columna.agregarValor(10);
        columna.agregarValor(20.5);
        columna.agregarValor(30);

        // Imprimir valores
        System.out.println("Valores en " + columna.obtenerNombre() + ":");
        for (int i = 0; i < columna.obtenerTamaño(); i++) {
            System.out.println(columna.obtenerValor(i) != null ? columna.obtenerValor(i) : "NA");
        }
    }

    public static void pruebaColumnaString() {
        ColumnaString columna = new ColumnaString("Columna de Cadenas");

        // Agregar valores
        columna.agregarValor("Hola");
        columna.agregarValor("Mundo");
        
        // Imprimir valores
        System.out.println("Valores en " + columna.obtenerNombre() + ":");
        for (int i = 0; i < columna.obtenerTamaño(); i++) {
            System.out.println(columna.obtenerValor(i) != null ? columna.obtenerValor(i) : "NA");
        }
    }

    public static void pruebaColumnaNA() {
        ColumnaNA columna = new ColumnaNA("Columna NA");
    
        // No agregues valores, simplemente imprime
        System.out.println("Valores en " + columna.obtenerNombre() + ":");
        for (int i = 0; i < columna.obtenerTamaño(); i++) {
            System.out.println(columna.obtenerValor(i) != null ? columna.obtenerValor(i) : "NA");
        }
    
        // Puedes imprimir una línea indicando que no se pueden agregar valores
        System.out.println("No se pueden agregar valores a esta columna.");
    }
    
}
