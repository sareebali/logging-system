public class ExQuotaExceeded extends Exception{
    public ExQuotaExceeded(){super("Loan quota exceeded.");}
    public ExQuotaExceeded(String message){super(message);}
}