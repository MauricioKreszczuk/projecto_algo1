package ExcepcionTabla;

public class ExcepcionNumeroDeColumnasInvalido extends RuntimeException {
    public ExcepcionNumeroDeColumnasInvalido(String mensaje) {
        super(mensaje);
    }
}