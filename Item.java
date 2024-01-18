import java.util.*;
public class Item implements Comparable<Item>{
    private String id;
    private String name;
    private Day arrDate;
    private ItemStatus status;
    private Member borrower;
    private Member onHoldFor;
    private Day onholdDate;
    private ArrayList<Member> requestList = new ArrayList<>();

    public Item(String id, String name) throws ExIDAlreadyInUse {
        this.id = id;
        this.name = name;
        this.arrDate = SystemDate.getInstance().clone();
        this.status = new ItemStatusAvailable();
        borrower = null;
        onHoldFor = null;
        onholdDate = null;
        Club.getInstance().addItem(this);
    }

    public void setBorrower(Member m){
        String stat[] = status.toString().split(" ");
        if ((stat[0]).compareTo("Borrowed") != 0) borrower = m;
    }

    public void setBorrowertoNull(){
        borrower = null;
    }

    public void setOnHoldFor(Member m){
        onHoldFor = m;
    }

    public Member getOnHoldFor(){
        return onHoldFor;
    }

    @Override
    public String toString() {
        //Learn: "-" means left-aligned
        return String.format("%-5s%-17s%11s   %s", id, name, arrDate, status);
    }

    public static String getListingHeader() {
        return String.format("%-5s%-17s%11s   %s", "ID", "Name", "  Arrival  ", "Status");
    }

    public String getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void changestatustoBorrowed(){
        if (!(status instanceof ItemStatusBorrowed)) status = new ItemStatusBorrowed(borrower, this);
    }

    public void changestatustoAvailable(){
        status = new ItemStatusAvailable();
    }

    public void changestatustoOnHold(){
        if (!(status instanceof ItemStatusOnhold)) setOnHoldDate();
    }

    public void setOnHoldDate(){
        onholdDate = SystemDate.getInstance().clone();
        onholdDate.addthreedays();
        status = new ItemStatusOnhold(onHoldFor, this);
    }

    public void setOnHoldDate(Day d){
        onholdDate = d;
        status = new ItemStatusOnhold(onHoldFor, this);
    }

    public void setOnHoldDatetoNull(){
        onholdDate = null;
    }

    public Day getOnHoldDate(){
        return onholdDate;
    }

    public ItemStatus getStatus(){
        return status;
    }

    public Member getBorrower(){
        return borrower;
    }

    public void addRequest(Member rm) throws ExAlreadyRequested, ExAlreadyBorrowed, NullPointerException{
        if (findRequester(rm.getID()) != null) throw new ExAlreadyRequested();
        else if (rm == borrower) throw new ExAlreadyBorrowed();
        else requestList.add(rm);
    }

    public void addRequest(int ind, Member rm) throws ExAlreadyRequested, ExAlreadyBorrowed, NullPointerException{
        if (findRequester(rm.getID()) != null) throw new ExAlreadyRequested();
        else if (rm == borrower) throw new ExAlreadyBorrowed();
        else requestList.add(ind, rm);
    }

    public void removeRequest(Member rm){
        requestList.remove(rm);
    }

    public int getNumOfRequests(){
        return requestList.size();
    }

    public String getIDsOfRequesters(){
        String IDs = "";
        for (Member m: requestList){
            IDs += m.getID() + " ";
        }
        IDs = IDs.strip();
        return IDs;
    }

    public Member findRequester(String id){
        for (Member m: requestList){
            if (id.compareTo(m.getID()) == 0) return m;
        }
        return null;
    }

    public int getIndofRequester(Member m){
        return requestList.indexOf(m);
    }

    public void PushRequest(Member pushed){
        requestList.add(0, pushed);
    }

    public Member PopRequest(){
        Member excluded = requestList.get(0);
        requestList.remove(0);
        return excluded;
    }

    @Override
    public int compareTo(Item i) {
        return this.id.compareTo(i.id);
    }

}