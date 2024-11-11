package ExcepcionTabla;

public class ExcepcionTipoDeDatoInvalido extends RuntimeException {

    public ExcepcionTipoDeDatoInvalido(String tipoEsperado, String tipoRecibido) {
        super("Tipo de dato inválido: se esperaba " + tipoEsperado + " pero se recibió " + tipoRecibido);
    }

    public ExcepcionTipoDeDatoInvalido(String string) {
        super(string);
    }
}