package Celda;


public class CeldaString extends Celda<String> implements definirCelda<String> {

    public CeldaString(String valor) {
        super(valor);
    }

    @Override
    public String obtenerValor() {
        return this.valor;
    }

    @Override
    public CeldaString copiaProfunda() {
        return new CeldaString(this.valor);
    }

    @Override
    public void establecerValor(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CeldaString other = (CeldaString) obj;
        return valor.equals(other.valor);  // Usa equals() de String para comparar
    }



}
