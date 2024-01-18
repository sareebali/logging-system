public class ExItemNotBorrowedByThisMemb extends Exception{
    public ExItemNotBorrowedByThisMemb(){super("The item is not borrowed by this member.");}
    public ExItemNotBorrowedByThisMemb(String message){super(message);}
}