package Celda;

public class CeldaNA extends Celda<String> {

    public CeldaNA() {
        super();
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
    public boolean equals(Object obj) { // Mantén la firma de Object
        if (this == obj) {
            return true; // Verifica si son el mismo objeto
        }
        if (obj == null || !(obj instanceof CeldaNA)) {
            return false; // Verifica si obj es null o no es del tipo esperado
        }
        // Cast seguro a CeldaNA para la comparación
        CeldaNA otraCelda = (CeldaNA) obj; 
        return true; // Aquí puedes agregar lógica adicional si es necesario
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
