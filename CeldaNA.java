public class CeldaNA extends Celda<Object> {

    public CeldaNA() {
        super();
    }

    @Override
    public String getTipo() {
        return "NA"; 
    }

    @Override
    public String toString() {
        return "NA";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CeldaNA;
    }

    @Override
    public int hashCode() {
            return java.util.Objects.hash("NA");
    }

    @Override
    public CeldaNA copiaProfunda() {
        return new CeldaNA();
    }
}
