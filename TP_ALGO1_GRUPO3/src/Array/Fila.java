package Array;

import java.util.List;
import java.util.Objects;

import Celda.Celda;

public class Fila extends ArrayCelda {

    public Fila(ArrayCelda array){
        super(array.obtenerNombre());
        this.establecerCeldas(array.obtenerCeldas());
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
        sb.append(nombre).append(" | "); 
        
        for (int i = 0; i < obtenerTamaño(); i++) {
            sb.append(obtenerValor(i) != null ? obtenerValor(i).toString() : "null").append(" | ");
        }
        
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 3); 
        }
    
        sb.append(" |"); 
        return sb.toString(); 
    }


        @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Fila otraFila = (Fila) obj;
        return Objects.equals(this.nombre, otraFila.nombre) &&
            Objects.equals(this.celdas, otraFila.celdas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, celdas);
    }
    
    
    
}
