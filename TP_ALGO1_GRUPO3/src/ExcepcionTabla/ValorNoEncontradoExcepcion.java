package ExcepcionTabla;

public class ValorNoEncontradoExcepcion extends ExcepcionTabla {

    public ValorNoEncontradoExcepcion(String valor) {
        super("El valor \"" + valor + "\" no se encontró en la tabla");
    }
}