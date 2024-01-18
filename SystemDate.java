public class SystemDate extends Day{
    private static SystemDate instance=null;

    private SystemDate(String sDay){
        super(sDay);
    }

    public static SystemDate getInstance(){
        return instance;
    }

    public static void createTheInstance(String sDay) {
        if (instance == null){
            instance = new SystemDate(sDay); //The instance created when this method is called like:
        }
        else{System.out.println("Cannot create one more system date instance.");}
    }
    
}