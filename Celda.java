public abstract class Celda<T> {

    T valor;
    final String tipo = this.getClass().getSimpleName();


    public Celda(T valor) {
        this.valor = valor;
    }

    public Celda() {
        this.valor = null;
    }


    public void definirValor(T valor) {
        this.valor = valor;
    }

    public String obtenerTipo() {
        return tipo;
    }

    public abstract Celda<T> copiaProfunda();  

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; 
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; 
        }
        Celda<?> other = (Celda<?>) obj;
        return java.util.Objects.equals(valor, other.valor);
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(valor);
    }

}
