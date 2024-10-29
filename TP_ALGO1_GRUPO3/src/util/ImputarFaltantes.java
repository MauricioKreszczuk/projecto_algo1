package util;
import Celda.*;
public interface ImputarFaltantes {
    public <T extends Celda<?>>void imputarNA(T nuevaCelda);
}
