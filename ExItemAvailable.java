public class ExItemAvailable extends Exception{
    public ExItemAvailable(){super("The item is currently available.");}
    public ExItemAvailable(String message){super(message);}
}