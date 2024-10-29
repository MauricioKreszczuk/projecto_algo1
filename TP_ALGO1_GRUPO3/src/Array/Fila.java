package Array;

import Celda.Celda;
import Celda.Celda.*;
import java.util.List;
import Array.ArrayCelda;

public class Fila extends ArrayCelda {

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Celda<?> celda : celdas) {
            sb.append(String.format("%-15s", celda.toString())).append(" | ");
        }
        return sb.toString();
    }
    
}
