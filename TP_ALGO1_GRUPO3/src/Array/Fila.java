package Array;

import java.util.List;
import java.util.Objects;

import Celda.Celda;

public class Fila extends ArrayCelda {


    public Fila(ArrayCelda array){
        super(array.obtenerNombre());
        for(Celda<?> celda : array.obtenerCeldas()){
            agregarCelda(celda);
        }
    }

    public Fila(List<Celda<?>> fila1Celdas) {
        super(fila1Celdas);
    }

    public Fila(String etiqueta) {
		super(etiqueta);
	}

	public Celda<?> obtenerCelda(int indice){
        return this.celdas.get(indice);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombre).append(" | "); // Agrega el nombre de la fila seguido de un separador
        
        for (int i = 0; i < obtenerTamaño(); i++) {
            //Fijarse que imprima NA en los lugares vacios 
            sb.append(obtenerValor(i) != null ? obtenerValor(i).toString() : "null").append(" | ");
        }
        
        // Eliminar el último separador " | " si hay elementos en la fila
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 3); // Elimina " | " del final
        }
    
        sb.append(" |"); // Agrega el separador final
        return sb.toString(); // Devuelve el resultado
    }


        @Override
    public boolean equals(Object obj) {
        // Verifica si el objeto es la misma instancia
        if (this == obj) {
            return true;
        }

        // Verifica que el objeto no sea null y que sea exactamente de la misma clase
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // Hace el cast seguro
        Fila otraFila = (Fila) obj;

        // Compara el nombre y las celdas
        return Objects.equals(this.nombre, otraFila.nombre) &&
            Objects.equals(this.celdas, otraFila.celdas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, celdas);
    }
    
    
    
}
