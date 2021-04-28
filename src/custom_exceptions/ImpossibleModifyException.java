package custom_exceptions;

public class ImpossibleModifyException extends Exception{
    public ImpossibleModifyException(String message){
        super(message);
    }
}
