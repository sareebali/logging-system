import java.util.ArrayList;
import java.util.Collections;

public class Club{
    private ArrayList<Member> allMembers;
    private ArrayList<Item> allItems;
    private static Club instance = new Club(); //The instance created when the class is loaded.

    private Club(){
        allMembers = new ArrayList<>();
        allItems = new ArrayList<>();
    }

    public static Club getInstance() {
        return instance;
    }

    public void addMember(Member m) throws ExIDAlreadyInUse  {
        if (findMember(m.getID()) != null){
            throw new ExIDAlreadyInUse();
        }
        else{
            allMembers.add(m);
            Collections.sort(allMembers);
        }
    }

    public void removeMember(Member m){
        allMembers.remove(m);
    }

    public Member findMember(String id){
        for (int i = 0; i < allMembers.size(); i++){
            if (id.compareTo(allMembers.get(i).getID())==0){
                return allMembers.get(i);
            }
        }
        return null;
    }

    public void listClubMembers() {
        System.out.println(Member.getListingHeader()); // Member
        for (Member m: allMembers)
            System.out.println(m); // m or m.toString()
        }

    public void addItem(Item i) throws ExIDAlreadyInUse  {
        if (findItem(i.getID()) != null){
            throw new ExIDAlreadyInUse();
        }
        else{
            allItems.add(i);
            Collections.sort(allItems);
        }
    }

    public void removeItem(Item i){
        allItems.remove(i);
    }

    public Item findItem(String id){
        for (int i = 0; i < allItems.size(); i++){
            if (id.compareTo(allItems.get(i).getID())==0){
                return allItems.get(i);
            }
        }
        return null;
    }

    public void listClubItems() {
        System.out.println(Item.getListingHeader());
        for (Item i: allItems)
            System.out.println(i);
    }

    public void checkOnHold(Day d){
        for (Item i: allItems){
            if (i.getOnHoldDate() != null){
                if (i.getOnHoldDate().compareTo(d) < 0){
                    System.out.println("On hold period is over for " + i.getID() + " " + i.getName() + ".");
                    if (i.getNumOfRequests() > 0){
                        Member onHoldMember = i.PopRequest();
                        onHoldMember.removeRequested(i);
                        i.setOnHoldFor(onHoldMember);
                        i.setOnHoldDate();
                        System.out.println("Item [" + i.getID() + " " + i.getName() + "] is ready for pick up by [" +
                        onHoldMember.getID() + " " + onHoldMember.getName() + "].  On hold due on " + i.getOnHoldDate() + ".");
                    }
                    else {
                        i.setOnHoldFor(null);
                        i.setOnHoldDatetoNull();
                        i.changestatustoAvailable();
                    }
                }
            }
        }
    }
}