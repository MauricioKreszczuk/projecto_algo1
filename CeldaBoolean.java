public class CeldaBoolean extends Celda<Boolean> implements obtenerValorCelda<Boolean> {

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


}