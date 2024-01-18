public class CmdCheckin extends Command {
    private Item i;
    private Member m;
    private Member onHoldMember;
    private boolean isBorrowtoOnHold;

    @Override
    public void execute(String[] s) {
        isBorrowtoOnHold = false;
        m = Club.getInstance().findMember(s[1]);
        i = Club.getInstance().findItem(s[2]);

        if(i.getBorrower() == m){
            m.removeBorrowed(i);
            i.setBorrowertoNull();
            if (i.getNumOfRequests() > 0) {
                onHoldMember = i.PopRequest();
                onHoldMember.removeRequested(i);
                i.setOnHoldFor(onHoldMember);
                isBorrowtoOnHold = true;
                i.changestatustoOnHold();
                System.out.println("Item [" + i.getID() + " " + i.getName() + "] is ready for pick up by [" +
                onHoldMember.getID() + " " + onHoldMember.getName() + "].  On hold due on " + i.getOnHoldDate() + ".");
            }
            else i.changestatustoAvailable();
            System.out.println("Done.");
            addtoUndolist(this);
            clearRedolist();
        }
        else{
            try {
                throw new ExItemNotBorrowedByThisMemb();
            } catch (ExItemNotBorrowedByThisMemb e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void undome() {
        boolean shouldContinue = true;

        i.setOnHoldFor(m); //setting borrower as OnHoldFor so that addBorrowed() works without giving ExItemNotAvailable

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
            i.changestatustoBorrowed();
            if (isBorrowtoOnHold){
                i.PushRequest(onHoldMember);
                i.setOnHoldFor(null);
                i.setOnHoldDatetoNull();
                try {
                    onHoldMember.addRequested(i);
                    System.out.println("Sorry. " + onHoldMember.getID() + " " + onHoldMember.getName() +
                    " please ignore the pick up notice for " + i.getID() + " " + i.getName() + ".");
                } catch (NullPointerException e) {
                    System.out.println("Member not found.");
                    shouldContinue = false;
                } catch (ExRequestQuotaExceeded e) {
                    System.out.println(e.getMessage());
                    shouldContinue = false;
                } catch (ExItemAvailable e) {
                    System.out.println(e.getMessage());
                    shouldContinue = false;
                }
            }
            addtoRedolist(this);
        }
    }

    @Override
    public void redome() {
        m.removeBorrowed(i);
        i.setBorrowertoNull();

        if (isBorrowtoOnHold) {
            onHoldMember = i.PopRequest();
            onHoldMember.removeRequested(i);
            i.setOnHoldFor(onHoldMember);
            i.changestatustoOnHold();
            System.out.println("Item [" + i.getID() + " " + i.getName() + "] is ready for pick up by [" +
            onHoldMember.getID() + " " + onHoldMember.getName() + "].  On hold due on " + i.getOnHoldDate() + ".");
        }
        else i.changestatustoAvailable();

        addtoUndolist(this);
    }
}
