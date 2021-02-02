package protonaut.model;

public class WebSocketMessage {
    private String action;
    private String to;
    private String body;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "WebSocketMessage{" +
                "action='" + action + '\'' +
                ", to='" + to + '\'' +
                ", body=" + body +
                '}';
    }
}
