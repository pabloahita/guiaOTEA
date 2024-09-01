package session;

public class StringPasser {


    private int flag;

    private static StringPasser instance;

    private int idTitle;

    private int idMsg;

    private String message;

    private StringPasser(int idTitle,int idMsg){
        setIdTitle(idTitle);
        setIdMsg(idMsg);
    }

    private StringPasser(String message){
        setMessage(message);
    }

    public static synchronized void createInstance(int idTitle,int idMsg){
        instance=new StringPasser(idTitle,idMsg);
    }

    public static synchronized void createInstance(String message){
        instance=new StringPasser(message);
    }

    public static synchronized StringPasser getInstance(){
        return instance;
    }

    public static synchronized void removeInstance(){
        instance=null;
    }

    public int getIdTitle() {
        return idTitle;
    }

    public void setIdTitle(int idTitle) {
        this.idTitle = idTitle;
    }

    public int getIdMsg() {
        return idMsg;
    }

    public void setIdMsg(int idMsg) {
        this.idMsg = idMsg;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
