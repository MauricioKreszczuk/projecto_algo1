package ExcepcionTabla;

public class ExcepcionIndiceFueraDeRango extends RuntimeException {
    public String mensaje;
    public ExcepcionIndiceFueraDeRango(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
        
    }

    public String obtenerMensaje() {
        return this.mensaje;
    }
}