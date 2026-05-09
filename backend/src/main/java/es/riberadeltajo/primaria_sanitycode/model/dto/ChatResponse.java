package es.riberadeltajo.primaria_sanitycode.model.dto;

public class ChatResponse {

    private String reply;
    private boolean error;

    public ChatResponse(String reply, boolean error) {
        this.reply = reply;
        this.error = error;
    }

    public static ChatResponse ok(String reply) {
        return new ChatResponse(reply, false);
    }

    public static ChatResponse error(String msg) {
        return new ChatResponse(msg, true);
    }

    public String getReply() { return reply; }
    public boolean isError() { return error; }
}