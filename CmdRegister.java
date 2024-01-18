public class CmdRegister extends Command {
    private Member m;
    private String id;

    @Override
    public void execute(String[] s) {
        id = s[1];
        try {
            m = new Member(s[1], s[2]);
            System.out.println("Done.");
            addtoUndolist(this);
            clearRedolist();
        } catch (ExIDAlreadyInUse e) {
            System.out.println("Member" + e.getMessage() + s[1] + " " + (Club.getInstance().findMember(s[1])).getName());
        }
    }

    @Override
    public void undome() {
        Club.getInstance().removeMember(m);
        addtoRedolist(this);
    }

    @Override
    public void redome() {
        try {
            Club.getInstance().addMember(m);
        } catch (ExIDAlreadyInUse e) {
            System.out.println(e.getMessage() + id + " " + (Club.getInstance().findMember(id)).getName());
        }
        addtoUndolist(this);
    }

}
