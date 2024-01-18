public class ExAlreadyRequested extends Exception{
    public ExAlreadyRequested(){super("The same member has already requested the item.");}
    public ExAlreadyRequested(String message){super(message);}
}