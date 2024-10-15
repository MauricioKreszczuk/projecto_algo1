package Celda;

import java.math.BigDecimal;

public class CeldaNumber extends Celda<Number> implements obtenerValorCelda<Number>, definirCelda {

    public CeldaNumber(){
        super();
    }

    public CeldaNumber(Number valor) {
        super(valor);
    }

    @Override
    public Number obtenerValor() {
        return this.valor;
    }

    @Override
    public CeldaNumber copiaProfunda() {
        if (valor == null) {
            return new CeldaNumber(null);
        }
    
        if (valor instanceof Integer) {
            return new CeldaNumber((Integer) valor);
        } else if (valor instanceof Double) {
            return new CeldaNumber((Double) valor);
        } else if (valor instanceof Float) {
            return new CeldaNumber((Float) valor);
        } else if (valor instanceof Long) {
            return new CeldaNumber((Long) valor);
        } else if (valor instanceof BigDecimal) {
            return new CeldaNumber(new BigDecimal(((BigDecimal) valor).toString()));
        } else if (valor instanceof Short) {
            return new CeldaNumber((Short) valor);
        } else if (valor instanceof Byte) {
            return new CeldaNumber((Byte) valor);
        } else {
            throw new IllegalArgumentException("Unexpected value: " + valor);
        }
}


    @Override
    public void establecerValor(Object valor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'establecerValor'");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; 
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; 
        }
        

        CeldaNumber other = (CeldaNumber) obj;
        return this.obtenerValor() == other.obtenerValor();
    }

    
}