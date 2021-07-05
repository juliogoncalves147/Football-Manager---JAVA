package Exceptions;

/**
 * Classe que trata as exceções de linha inválida
 */
public class LinhaInvalidaException extends Exception{
    public LinhaInvalidaException(){
        super();
    }
    public LinhaInvalidaException(String e){
        super(e);
    }
}
