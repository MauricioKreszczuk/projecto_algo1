package ExcepcionTabla;

public class IndiceFueraDeRangoExcepcion extends ExcepcionTabla {

    // public IndiceFueraDeRangoExcepcion(int comienzoRango, int finalRango , int maxfilas) {
    //     super("El rango especificado (" + comienzoRango + " a " + finalRango + ") está fuera del rango permitido (0 a " + maxfilas + ")");
    // }
    public IndiceFueraDeRangoExcepcion(String mensaje) {
        super(mensaje);
    }
}