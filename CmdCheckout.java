public class CmdCheckout extends Command {
    private Item i;
    private Member m;
    private Member onHoldMember;
    private boolean isOnHoldtoBorrow;
    private Day onHoldDate;

    @Override
    public void execute(String[] s) {
        boolean shouldContinue = true;
        isOnHoldtoBorrow = false;
        m = Club.getInstance().findMember(s[1]);
        i = Club.getInstance().findItem(s[2]);
        onHoldMember = i.getOnHoldFor();

        try {
            i.setBorrower(m);
        } catch (NullPointerException e) {
            System.out.println("Item not found.");
            shouldContinue = false;
        }
        if (shouldContinue){
            try {
                try {
                    try {
                        m.addBorrowed(i);
                    } catch (ExQuotaExceeded e) {
                        System.out.println("Loan quota exceeded.");
                        shouldContinue = false;
                        i.setBorrower(null);
                    }
                } catch (ExItemNotAvailable e) {
                    System.out.println("Item not available.");
                    shouldContinue = false;
                    i.setBorrower(null);
                }
            } catch (NullPointerException e) {
                System.out.println("Member not found.");
                shouldContinue = false;
                i.setBorrower(null);
            }
            if (shouldContinue){
                if (onHoldMember == m){
                    isOnHoldtoBorrow = true;
                    onHoldDate = i.getOnHoldDate();
                    i.setOnHoldDatetoNull();
                    i.setOnHoldFor(null);
                }
                i.changestatustoBorrowed();
                System.out.println("Done.");
                addtoUndolist(this);
                clearRedolist();
            }
        }
    }

    @Override
    public void undome() {
        i.setBorrower(null);
        m.removeBorrowed(i);
        if (isOnHoldtoBorrow){
            i.changestatustoOnHold();
            i.setOnHoldFor(m);
            i.setOnHoldDate(onHoldDate);
        }
        else i.changestatustoAvailable();
        addtoRedolist(this);
    }

    @Override
    public void redome() {
        boolean shouldContinue = true;
        try {
            try {
                m.addBorrowed(i);
            } catch (ExQuotaExceeded e) {
                System.out.println("Loan quota exceeded.");
                shouldContinue = false;
            }
        } catch (ExItemNotAvailable e) {
            System.out.println("Item not available.");
            shouldContinue = false;
        }
        if (shouldContinue){
            i.setBorrower(m);
            if (onHoldMember == m){
                isOnHoldtoBorrow = true;
                i.setOnHoldDatetoNull();
                i.setOnHoldFor(null);
            }
            i.changestatustoBorrowed();
            addtoUndolist(this);
        }
    }
    
}
