# grupo3Algoritmos1 - Ejemplos de prácticas de código

Definición de una clase:

package com.introduccion;

import java.time.LocalDate;

public class Libro {
  private String titulo;
  private String autor;
  private int  añoPublicacion;

  public Libro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.añoPublicacion = LocalDate.now().getYear();
  }
    
  public Libro(String titulo, String autor, int añoPublicacion) {
        this.titulo = titulo;
        this.autor = autor;
        this.añoPublicacion = añoPublicacion;
  }

  public void mostrarDetalles() {
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Año de Publicación: " + añoPublicacion);
  }
    
  public static void main(String[] args) {
        Libro libro1 = new Libro("El señor de los anillos", "J.R.R Tolkien");
        Libro libro2 = new Libro("Harry Potter y el cáliz de fuego", "J.K Rowling", 2000);
        libro1.mostrarDetalles();
        libro2.mostrarDetalles();
    }

}


// Las variables siempre las vamos a escribir en camelCase (primera palabra en minusculas, luego cada nueva empezará con mayuscula)

// Las indentaciones tienen que respetar la jerarquía dentro de las clases y las llaves ir en el renglón siguiente a la última línea. La llave que contiene a todos los métodos, cerrará bien separada del último método, con una línea de por medio y respetando indentación.

// Para los getters y setters:
Todo metodo del tipo get...() lo vamos a llamar: obtener...()
Y todo metodo set...(), lo vamos a llamar definir...()

Ejemplo de estructura if (Atención a sintaxis principalmente):

int edad = 22;

if (edad >= 18) {
    System.out.println("Sos mayor de edad.");
} else {
    System.out.println("Sos menor de edad.");
}

If-Else anidados:

int nota = 85;

if (nota >= 90) {
    System.out.println("Aprobaste con Excelente.");
} else if (nota >= 80) {
    System.out.println("Aprobaste con Muy bueno.");
} else if (nota >= 70) {
    System.out.println("Aprobaste con Bueno.");
} else if (nota >= 60) {
    System.out.println("Aprobaste con Suficiente.");
} else {
    System.out.println("Reprobaste.");
}

// Con el resto de las estructuras de control, nos vamos a basar en el repositorio de la materia para seguir un orden de escritura. (ver: https://github.com/mapreu/algoritmos1/tree/main/01_introduccion)


Tipos de datos (Ver en caso de dudas):
![image](https://github.com/user-attachments/assets/77ef1e93-06d6-49fa-9d72-e54dc5dfcb56)


