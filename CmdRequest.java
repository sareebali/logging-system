public class CmdRequest extends Command {
    private Item i;
    private Member m;
    //private ItemStatus oldstatus;

    @Override
    public void execute(String[] s) {
        boolean shouldContinue = true;
        m = Club.getInstance().findMember(s[1]);
        i = Club.getInstance().findItem(s[2]);

        if (m==null) {
            try {
                throw new ExMemberNotFound();
            } catch (ExMemberNotFound e) {
                System.out.println("Member not found.");
                shouldContinue = false;
            }
        }

        if (shouldContinue){
            try {
                i.addRequest(m);
                //oldstatus = i.getStatus();
            } catch (NullPointerException e) {
                System.out.println("Item not found.");
                shouldContinue = false;
            } catch (ExAlreadyRequested e) {
                System.out.println(e.getMessage());
                shouldContinue = false;
            } catch (ExAlreadyBorrowed e) {
                System.out.println(e.getMessage());
                shouldContinue = false;
            }
        }

        if(shouldContinue){
            try {
                m.addRequested(i);
            } catch (NullPointerException e) {
                System.out.println("Member not found.");
                i.removeRequest(m);
                shouldContinue = false;
            } catch (ExItemAvailable e) {
                System.out.println(e.getMessage());
                i.removeRequest(m);
                shouldContinue = false;
            } catch (ExRequestQuotaExceeded e) {
                System.out.println(e.getMessage());
                i.removeRequest(m);
                shouldContinue = false;
            }
            if(shouldContinue){
                System.out.println("Done. This request is no. " + i.getNumOfRequests() + " in the queue.");
                addtoUndolist(this);
                clearRedolist();
            }
        }
    }

    @Override
    public void undome() {
        m.removeRequested(i);
        i.removeRequest(m);
       // if (i.getNumOfRequests() < 1) i.changestatustoBorrowed();
        addtoRedolist(this);
    }

    @Override
    public void redome() {
        boolean shouldContinue = true;

        if (m==null) {
            try {
                throw new ExMemberNotFound();
            } catch (ExMemberNotFound e) {
                System.out.println("Member not found.");
                shouldContinue = false;
            }
        }

        try {
            i.addRequest(m);
        } catch (NullPointerException e) {
            System.out.println("Item not found.");
            m.removeRequested(i);
            shouldContinue = false;
        } catch (ExAlreadyRequested e) {
            System.out.println(e.getMessage());
            m.removeRequested(i);
            shouldContinue = false;
        } catch (ExAlreadyBorrowed e) {
            System.out.println(e.getMessage());
            m.removeRequested(i);
            shouldContinue = false;
        }

        if(shouldContinue){
            try {
                m.addRequested(i);
            } catch (NullPointerException e) {
                System.out.println("Member not found.");
                i.removeRequest(m);
                shouldContinue = false;
            } catch (ExItemAvailable e) {
                System.out.println(e.getMessage());
                i.removeRequest(m);
                shouldContinue = false;
            } catch (ExRequestQuotaExceeded e) {
                System.out.println(e.getMessage());
                i.removeRequest(m);
                shouldContinue = false;
            }
            if(shouldContinue){
                addtoUndolist(this);
            }
        }
    }

}
