public class CeldaString extends Celda<String> {

    public CeldaString(){
        super();
    }

    public CeldaString(String valor) {
        super(valor);
        //TODO Auto-generated constructor stub
    }

    @Override
    public CeldaString copiaProfunda() {
        return new CeldaString(this.valor);
    }


}
