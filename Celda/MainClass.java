package Celda;

public class MainClass { // Cambia "MainClass" al nombre que desees
    public static void main(String[] args) {
        // Aquí puedes agregar el código que desees ejecutar
        
        CeldaNumber celda1 = new CeldaNumber(1);
        CeldaNumber celda2 = new CeldaNumber(1.0);
        System.out.println(celda1);
        CeldaNA celdaNA = new CeldaNA();
        System.out.println(celdaNA);
        System.out.println(celda1.equals(celda2));
        System.out.println(1 == 1.0);
    }
}

