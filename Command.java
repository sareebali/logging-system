import java.util.*;

public abstract class Command {
    private static ArrayList<Command> undolist = new ArrayList<>();
    private static ArrayList<Command> redolist = new ArrayList<>();

    public abstract void undome();
    public abstract void redome();
    public abstract void execute(String[] s);

    public static void addtoUndolist(Command c){undolist.add(c);}
    public static void addtoRedolist(Command c){redolist.add(c);}
    public static void clearRedolist(){redolist.clear();}

    public static void undoCommand() throws IndexOutOfBoundsException {
        try{
            undolist.remove(undolist.size()-1).undome();
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Nothing to undo.");
        }
    }

    public static void redoCommand() throws IndexOutOfBoundsException {
        try{
            redolist.remove(redolist.size()-1).redome();
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Nothing to redo.");
        }
    }



}
