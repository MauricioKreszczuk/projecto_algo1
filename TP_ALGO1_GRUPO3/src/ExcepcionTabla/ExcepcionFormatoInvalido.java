package ExcepcionTabla;

public class ExcepcionFormatoInvalido extends RuntimeException {
    public ExcepcionFormatoInvalido(String mensaje) {
        super(mensaje);
    }
}