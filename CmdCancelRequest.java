public class CmdCancelRequest extends Command {
    private Item i;
    private Member m;
    private int requesterInd;

    @Override
    public void execute(String[] s) {
        boolean shouldContinue = true;
        m = Club.getInstance().findMember(s[1]);
        i = Club.getInstance().findItem(s[2]);

        if (i == null) {
            try {
                throw new ExItemNotFound();
            } catch (ExItemNotFound e) {
                System.out.println(e.getMessage());
                shouldContinue = false;
            }
        }

        if (shouldContinue && m == null) {
            try {
                throw new ExMemberNotFound();
            } catch (ExMemberNotFound e) {
                System.out.println(e.getMessage());
                shouldContinue = false;
            }
        }

        if (shouldContinue && i.findRequester(m.getID()) == null){
            try {
                throw new ExRequestNotFound();
            } catch (ExRequestNotFound e) {
                System.out.println(e.getMessage());
                shouldContinue = false;
            }
        }

        if(shouldContinue){
            requesterInd = i.getIndofRequester(i.findRequester(m.getID()));
            i.removeRequest(m);
            m.removeRequested(i);
            System.out.println("Done.");
            addtoUndolist(this);
            clearRedolist();
        }

    }

    @Override
    public void undome() {
        boolean shouldContinue = true;

        try {
            i.addRequest(requesterInd, m);
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

        try {
            m.addRequested(i);
        } catch (NullPointerException e) {
            System.out.println("Member not found.");
            i.removeRequest(m);
            shouldContinue = false;
        } catch (ExRequestQuotaExceeded e) {
            System.out.println(e.getMessage());
            i.removeRequest(m);
            shouldContinue = false;
        } catch (ExItemAvailable e) {
            System.out.println(e.getMessage());
            i.removeRequest(m);
            shouldContinue = false;
        }

        if (shouldContinue) addtoRedolist(this);
    }

    @Override
    public void redome() {
        i.removeRequest(m);
        m.removeRequested(i);
        addtoUndolist(this);
    }

}
