public class CmdArrive extends Command {
    private Item i;
    private String id;

    @Override
    public void execute(String[] s) {
        id = s[1];
        try {
            i = new Item(s[1], s[2]);
            System.out.println("Done.");
            addtoUndolist(this);
            clearRedolist();
        } catch (ExIDAlreadyInUse e) {
            System.out.println("Item" + e.getMessage() + s[1] + " " + (Club.getInstance().findItem(s[1])).getName());
        }
    }

    @Override
    public void undome() {
        Club.getInstance().removeItem(i);
        addtoRedolist(this);
    }

    @Override
    public void redome() {
        try {
            Club.getInstance().addItem(i);
        } catch (ExIDAlreadyInUse e) {
            System.out.println(e.getMessage() + id + " " + (Club.getInstance().findItem(id)).getName());
        }
        addtoUndolist(this);
    }

}
