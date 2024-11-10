package ExcepcionTabla;

public class ExcepcionOperadorInvalido extends RuntimeException {
    public ExcepcionOperadorInvalido(String mensaje){
        super(mensaje);
    }
}
