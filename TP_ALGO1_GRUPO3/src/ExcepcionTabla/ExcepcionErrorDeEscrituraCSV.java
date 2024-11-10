package ExcepcionTabla;

public class ExcepcionErrorDeEscrituraCSV extends RuntimeException {
    public ExcepcionErrorDeEscrituraCSV(String mensaje){
        super(mensaje);
    }
}
