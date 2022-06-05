package Model.Entity;

public class Message {

    private int id;

    private String message;

    private int msgType;

    private boolean isNotify;

    public Message() {
        isNotify = false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNotify(boolean isNotify) {
        this.isNotify = isNotify;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getId() {
        return id;
    }

    public int getMsgType() {
        return msgType;
    }

    public String getMessage() {
        return message;
    }

    public boolean getNotify() {
        return isNotify;
    }
}
