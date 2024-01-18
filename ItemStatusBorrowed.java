public class ItemStatusBorrowed implements ItemStatus{
    private Member borrower;
    private Day loanDate;
    private Item requestedItem;

    public ItemStatusBorrowed(Member b, Item i){
        borrower = b;
        loanDate = SystemDate.getInstance().clone();
        requestedItem = i;
    }

    @Override
    public String toString(){
        if (requestedItem.getNumOfRequests() < 1)
        return "Borrowed by " + borrower.getID() + " " + borrower.getName() + " on " + loanDate;

        return "Borrowed by " + borrower.getID() + " " + borrower.getName() +
        " on " + loanDate + " + " + requestedItem.getNumOfRequests() + " request(s): " +
        requestedItem.getIDsOfRequesters();
    }
}