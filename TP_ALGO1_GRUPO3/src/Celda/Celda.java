package Celda;

public abstract class Celda<T> {
    T valor;

    public Celda(T valor) {
        this.valor = valor;
    }


    public T obtenerValor(){
        return this.valor;
    }

    
    public abstract Celda<T> copiaProfunda();  

    public void definirValor(T valor){
        this.valor = valor;
    }

    @Override
    public String toString() {
        return String.valueOf(this.valor);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(valor);
    }


    






}