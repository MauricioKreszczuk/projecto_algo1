package Celda;

import java.math.BigDecimal;

public class CeldaNumber extends Celda<Number> implements definirCelda<Number> {


    public CeldaNumber(Number valor) {
        super(valor);
    }

    public CeldaNumber(Number valor, Boolean convertir){
        super(convertirANumeroEspecifico(valor));
    }

    public static Number convertirANumeroEspecifico(Number numero) {
        // Si es decimal, conviértelo a Float o Double
        if (numero.doubleValue() % 1 != 0) { // Si tiene decimales
        if (numero.doubleValue() >= Float.MIN_VALUE && numero.doubleValue() <= Float.MAX_VALUE) {
        return numero.floatValue(); // Devuelve Float si cabe
        }
        return numero.doubleValue(); // Devuelve Double si no cabe en Float
        } else {
        // Si es entero, conviértelo a Byte, Short, Integer o Long
        long longValue = numero.longValue();
        
        if (longValue >= Byte.MIN_VALUE && longValue <= Byte.MAX_VALUE) {
        return numero.byteValue(); // Devuelve Byte si cabe
        } else if (longValue >= Short.MIN_VALUE && longValue <= Short.MAX_VALUE) {
        return numero.shortValue(); // Devuelve Short si cabe
        } else if (longValue >= Integer.MIN_VALUE && longValue <= Integer.MAX_VALUE) {
        return numero.intValue(); // Devuelve Integer si cabe
        }
        return longValue; // Devuelve Long si no cabe en tipos más bajos
        }
    }

    @Override
    public Number obtenerValor() {
        return this.valor;
    }
    
    @Override
    public void establecerValor(Number valor) {
        this.valor = valor;
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CeldaNumber other = (CeldaNumber) obj;
        if (this.valor == null || other.valor == null) {
            return this.valor == other.valor;  // Ambos son null
        }
        return Double.compare(this.valor.doubleValue(), other.valor.doubleValue()) == 0;
    }


    @Override
    public int hashCode(){
        if (valor==null){
            return 0;
        }

        return Double.hashCode(valor.doubleValue());
    }


    
}