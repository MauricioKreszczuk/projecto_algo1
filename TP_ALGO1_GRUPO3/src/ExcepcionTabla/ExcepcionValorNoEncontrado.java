package ExcepcionTabla;

public class ExcepcionValorNoEncontrado extends RuntimeException {

    public ExcepcionValorNoEncontrado(String valor) {
        super("El valor \"" + valor + "\" no se encontró en la tabla");
    }
}