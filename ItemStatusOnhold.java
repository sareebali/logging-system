public class ItemStatusOnhold implements ItemStatus{
    private Member onHoldFor;
    private Day onHoldDate;
    private Item requestedItem;

    public ItemStatusOnhold(Member b, Item i){
        onHoldFor = b;
        onHoldDate = i.getOnHoldDate();
        requestedItem = i;
    }

    @Override
    public String toString(){
        if (requestedItem.getNumOfRequests() > 0)
        return "On holdshelf for " + onHoldFor.getID() + " " + onHoldFor.getName() + " until " +
        onHoldDate + " + " + requestedItem.getNumOfRequests() + " request(s): " +
        requestedItem.getIDsOfRequesters();

        return "On holdshelf for " + onHoldFor.getID() + " " + onHoldFor.getName() + " until " + onHoldDate;
    }
}