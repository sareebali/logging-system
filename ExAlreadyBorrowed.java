public class ExAlreadyBorrowed extends Exception{
    public ExAlreadyBorrowed(){super("The item is already borrowed by the same member.");}
    public ExAlreadyBorrowed(String message){super(message);}
}