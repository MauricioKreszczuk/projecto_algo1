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
        return switch (valor) {
            case null -> new CeldaNumber(null);
            case Integer i -> new CeldaNumber(Integer.valueOf(i));
            case Double d -> new CeldaNumber(Double.valueOf(d));
            case Float f -> new CeldaNumber(Float.valueOf(f));
            case Long l -> new CeldaNumber(Long.valueOf(l));
            case BigDecimal bd -> new CeldaNumber(new BigDecimal(bd.toString()));
            case Short s -> new CeldaNumber(Short.valueOf(s));
            case Byte b -> new CeldaNumber(Byte.valueOf(b));
            //Añadir error al Exceptions despues
            default -> throw new IllegalArgumentException("Unexpected value: " + valor);
    };
}

    @Override
    public void establecerValor(Object valor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'establecerValor'");
    }

}