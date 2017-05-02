/**
 * Created by Rizzah on 5/2/2017.
 */

public class ChatMessage {
    private String mUsername = "Unknown";
    private String mMessage = "---";

    public ChatMessage(String user, String msg) {
        mUsername = user;
        mMessage = msg;
        return;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getMessage() {
        return mMessage;
    }

    public String toString() {
        return mUsername + ":\n" + mMessage;
    }
}
