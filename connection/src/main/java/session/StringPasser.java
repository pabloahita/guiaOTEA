package session;

public class StringPasser {

    private String string;

    private int flag;

    private static StringPasser instance;

    private StringPasser(String string){
        setString(string);
    }

    public static synchronized void createInstance(String string){
        instance=new StringPasser(string);
    }

    public static synchronized StringPasser getInstance(){
        return instance;
    }

    public static synchronized void removeInstance(){
        instance=null;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
