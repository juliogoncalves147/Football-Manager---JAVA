package Exceptions;

/**
 * Classe que trata as exceções de elementos não existentes
 */
public class NaoExisteException extends Exception{
    public NaoExisteException(){
        super();
    }
    public NaoExisteException(String msg){
        super(msg);
    }
}
