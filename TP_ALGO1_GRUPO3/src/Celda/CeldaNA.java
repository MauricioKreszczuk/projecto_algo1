package Celda;

public class CeldaNA extends Celda<String> {

    public CeldaNA() {
        super(null);
    }

    @Override
    public String obtenerTipo() {
        return "NA"; 
    }

    @Override
    public String toString() {
        return "NA";
    }

    @Override
    public String obtenerValor(){
        return null;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash("NA");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CeldaNA)) {
            return false;
        }
        return true;  // Todas las celdas NA son iguales
    }


    @Override
    public CeldaNA copiaProfunda() {
        return new CeldaNA();
    }
}
