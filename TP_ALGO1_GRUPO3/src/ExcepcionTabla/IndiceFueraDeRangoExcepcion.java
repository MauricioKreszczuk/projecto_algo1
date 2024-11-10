package ExcepcionTabla;

public class IndiceFueraDeRangoExcepcion extends RuntimeException {
    public String mensaje;
    public IndiceFueraDeRangoExcepcion(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
        
    }

    public String obtenerMensaje() {
        return this.mensaje;
    }
}