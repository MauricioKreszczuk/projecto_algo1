public class CeldaString extends Celda<String> implements obtenerValorCelda<String> {

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


}
