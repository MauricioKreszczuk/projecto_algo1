package ExcepcionTabla;

public abstract class ExcepcionTabla extends Exception {
    protected String mensaje;

    public ExcepcionTabla(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
    }
    
    public String obtenerMensaje(){
        return this.mensaje;
    }
}