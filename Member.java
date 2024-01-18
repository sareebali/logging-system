import java.util.*;
public class Member implements Comparable<Member>{
    private String id;
    private String name;
    private Day joinDate;
    private ArrayList<Item> BorrowList = new ArrayList<>();
    private ArrayList<Item> RequestedList = new ArrayList<>();

    public Member(String id, String name) throws ExIDAlreadyInUse {
        this.id = id;//Set id, name, joinDate
        this.name = name;
        this.joinDate = SystemDate.getInstance().clone();
        Club.getInstance().addMember(this);
    }

    @Override
    public String toString() {
        //Learn: "-" means left-aligned
        return String.format("%-5s%-9s%11s%7d%13d", id, name, joinDate, BorrowList.size(), RequestedList.size());
    }

    public static String getListingHeader() {
        return String.format("%-5s%-9s%11s%11s%13s", "ID", "Name",
       "Join Date ", "#Borrowed", "#Requested");
    }

    public String getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void addBorrowed(Item i) throws ExItemNotAvailable, ExQuotaExceeded{
        String stat[] = i.getStatus().toString().split(" ");
        if (BorrowList.size() < 6 && (stat[0]).compareTo("Borrowed") != 0 && 
        ((stat[0]).compareTo("On") != 0 || i.getOnHoldFor() == this)) BorrowList.add(i);
        else if (BorrowList.size() > 5) throw new ExQuotaExceeded();
        else throw new ExItemNotAvailable();
    }

    public void removeBorrowed(Item i){
        BorrowList.remove(i);
    }

    public void addRequested(Item i) throws ExRequestQuotaExceeded, ExItemAvailable, NullPointerException{
        if (RequestedList.size() < 3 && i.getStatus().toString().compareTo("Available") != 0 && 
        i.getOnHoldFor() != this) RequestedList.add(i);
        else if (RequestedList.size() > 2) throw new ExRequestQuotaExceeded();
        else throw new ExItemAvailable();
    }

    public void removeRequested(Item i){
        RequestedList.remove(i);
    }

    @Override
    public int compareTo(Member o) {
        return this.id.compareTo(o.id);
    }

}