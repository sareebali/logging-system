public class ExInsufficientCommandArguments extends Exception{
    public ExInsufficientCommandArguments(){super("Insufficient command arguments.");}
    public ExInsufficientCommandArguments(String message){super(message);}
}