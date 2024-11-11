package ExcepcionTabla;

public class ExcepcionFilaVacia extends RuntimeException {
    public ExcepcionFilaVacia(String mensaje) {
        super(mensaje);
    }
}