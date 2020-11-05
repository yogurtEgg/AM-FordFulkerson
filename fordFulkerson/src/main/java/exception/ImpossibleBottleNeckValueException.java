package exception;

/**
 * @author Paede
 * @version 25.10.2020
 */
public class ImpossibleBottleNeckValueException extends Exception {

    public ImpossibleBottleNeckValueException(String message){
        super(message);
    }

    public ImpossibleBottleNeckValueException(String message, Throwable cause){
        super(message, cause);
    }
}
