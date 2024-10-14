package Celda;

public class CeldaBoolean extends Celda<Boolean> implements obtenerValorCelda<Boolean>, definirCelda {

    public CeldaBoolean(){
        super();
    }

    public CeldaBoolean(Boolean valor) {
        super(valor);
    }
    
    @Override
    public Boolean obtenerValor() {
        return this.valor;
    }
    
    @Override
    public CeldaBoolean copiaProfunda() {
    return new CeldaBoolean(Boolean.valueOf(this.valor));  
    }

    @Override
    public void establecerValor(Object valor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'establecerValor'");
    }


}