import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import Array.Columnas.Columna;
import Celda.Celda;

public class Filtro{

    private static <T> List<T> generizar(Columna col){
        List<T> valores = new ArrayList<>();
        for (Celda celda : col.obtenerCeldas()){
            T valor = (T) celda.obtenerValor();
            valores.add(valor);
        }
        return valores;
    }

    @SuppressWarnings("unchecked") //En tabla se pasa "criterio" como lambda, pasar a String nombreCol
    public static <T> List<Integer> filtrar(Columna columna, Predicate<Object> criterio) {
        List<T> valores = generizar(columna);

        List<Integer> filtrados = new ArrayList<>();
        for (int i = 0; i < valores.size() ; i++){
            if(criterio.test(valores.get(i))){
                filtrados.add(i);
            }
        }
        return filtrados;
        //En Tabla se llama y se crea una tabla nueva o sobreescribe la primera segun un inplace
    }

    // Falta ver como permitir anidar criterios
}