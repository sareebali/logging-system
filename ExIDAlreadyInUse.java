public class ExIDAlreadyInUse extends Exception{
    public ExIDAlreadyInUse(){super(" ID already in use: ");}
    public ExIDAlreadyInUse(String message){super(message);}
}