package exception;

/**
 * @author Paede
 * @version 24.09.2020
 */
public class ImpossibleOrderException extends Exception {

    public ImpossibleOrderException(String message){
        super(message);
    }

    public ImpossibleOrderException(String message, Throwable cause){
        super(message, cause);
    }
}
