public class CmdStartNewDay extends Command {
    private String currentday;
    private String oldday;

    @Override
    public void execute(String[] s) {
        oldday = SystemDate.getInstance().toString();
        currentday = s[1];
        SystemDate.getInstance().set(currentday);

        Day thisday = new Day(s[1]);
        Club.getInstance().checkOnHold(thisday);

        System.out.println("Done.");
        addtoUndolist(this);
        clearRedolist();
    }

    @Override
    public void undome() {
        SystemDate.getInstance().set(oldday);
        addtoRedolist(this);
    }

    @Override
    public void redome() {
        SystemDate.getInstance().set(currentday);
        addtoUndolist(this);
    }

}
