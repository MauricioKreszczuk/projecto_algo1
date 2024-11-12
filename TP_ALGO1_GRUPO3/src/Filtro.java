import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import Array.Columnas.Columna;
import Celda.Celda;

public class Filtro{

    private static <T> List<T> generizar(Columna col){ //Obtengo los valores de cada celda para usar genéricos en filtrar()
        List<T> valores = new ArrayList<>();
        for (Celda celda : col.obtenerCeldas()){
            T valor = (T) celda.obtenerValor();
            valores.add(valor);
        }
        return valores;
    }

    @SuppressWarnings("unchecked") 
    public static <T> List<Integer> filtrar(Columna columna, Predicate<Object> criterio) {
        List<T> valores = generizar(columna);

        List<Integer> filtrados = new ArrayList<>();
        for (int i = 0; i < valores.size() ; i++){ //itero los valores de cada celda como genéricos
            if(criterio.test(valores.get(i))){
                filtrados.add(i);
            }
        }
        return filtrados; //Devuelvo una lista de los índices que cumplen la condición en la columna.
    }


}