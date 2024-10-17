package Celda;

public class CeldaBoolean extends Celda<Boolean> implements definirCelda<Boolean> {

    public CeldaBoolean(Boolean valor) {
        super(valor);
    }
    
    
    @Override
    public CeldaBoolean copiaProfunda() {
    return new CeldaBoolean(Boolean.valueOf(this.valor));  
    }

    @Override
    public void establecerValor(Boolean valor) {
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
        CeldaBoolean other = (CeldaBoolean) obj;
        return valor.equals(other.valor);  // Usa equals() para comparar valores booleanos
    }



}