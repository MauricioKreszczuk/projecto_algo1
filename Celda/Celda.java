package Celda;

public abstract class Celda<T> {

    T valor;
    final String tipo = this.getClass().getSimpleName();


    public Celda(T valor) {
        this.valor = valor;
    }

    public String obtenerTipo() {
        return tipo;
    }

    public T obtenerValor(){
        return this.valor;
    }

    
    public abstract Celda<T> copiaProfunda();  



    @Override
    public String toString() {
        return String.valueOf(valor);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(valor, tipo);
    }
    






}