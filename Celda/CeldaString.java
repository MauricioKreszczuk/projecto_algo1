package Celda;


public class CeldaString extends Celda<String> implements obtenerValorCelda<String>, definirCelda {

    public CeldaString(){
        super();
    }

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
    public void establecerValor(Object valor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'establecerValor'");
    }


}
