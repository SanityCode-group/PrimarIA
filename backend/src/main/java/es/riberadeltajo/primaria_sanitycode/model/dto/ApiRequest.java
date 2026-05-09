package es.riberadeltajo.primaria_sanitycode.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ApiRequest {
    private List<ChatMessage> history;

    @JsonProperty("new_message")
    private ChatMessage newMessage;

    public ApiRequest() {}

    public ApiRequest(List<ChatMessage> history, ChatMessage newMessage) {
        this.history = history;
        this.newMessage = newMessage;
    }

    public List<ChatMessage> getHistory() { return history; }
    public void setHistory(List<ChatMessage> history) { this.history = history; }

    public ChatMessage getNewMessage() { return newMessage; }
    public void setNewMessage(ChatMessage newMessage) { this.newMessage = newMessage; }
}