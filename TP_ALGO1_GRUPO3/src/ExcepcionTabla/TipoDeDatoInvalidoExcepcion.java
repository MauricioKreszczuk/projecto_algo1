package ExcepcionTabla;

public class TipoDeDatoInvalidoExcepcion extends ExcepcionTabla {

    public TipoDeDatoInvalidoExcepcion(String tipoEsperado, String tipoRecibido) {
        super("Tipo de dato inválido: se esperaba " + tipoEsperado + " pero se recibió " + tipoRecibido);
    }
}