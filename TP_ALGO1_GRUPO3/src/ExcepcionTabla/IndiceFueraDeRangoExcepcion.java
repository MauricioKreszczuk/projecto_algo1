package ExcepcionTabla;

public class IndiceFueraDeRangoExcepcion extends ExcepcionTabla {

    public IndiceFueraDeRangoExcepcion(int indice, int max) {
        super("El índice " + indice + " está fuera del rango permitido (0 a " + max + ")");
    }
    public IndiceFueraDeRangoExcepcion(String mensaje) {
        super(mensaje);
    }
}