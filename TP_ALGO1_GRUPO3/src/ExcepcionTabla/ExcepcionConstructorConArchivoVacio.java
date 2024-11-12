package ExcepcionTabla;

public class ExcepcionConstructorConArchivoVacio extends RuntimeException{
    public ExcepcionConstructorConArchivoVacio(String mensaje){
        super(mensaje);
    }
}