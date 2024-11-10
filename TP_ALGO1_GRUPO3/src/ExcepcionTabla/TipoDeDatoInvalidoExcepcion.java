package ExcepcionTabla;

public class TipoDeDatoInvalidoExcepcion extends RuntimeException {

    public TipoDeDatoInvalidoExcepcion(String tipoEsperado, String tipoRecibido) {
        super("Tipo de dato inválido: se esperaba " + tipoEsperado + " pero se recibió " + tipoRecibido);
    }

    public TipoDeDatoInvalidoExcepcion(String string) {
        super(string);
    }
}