package com.study.websocket.chat.dto;

public class MessageDto {
    private String from;
    private String message;
    private String reply;

    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getReply() {
        return reply;
    }
    public void setReply(String reply) {
        this.reply = reply;
    }
    @Override
    public String toString() {
        return "MessageDto{" + "from='" + from + '\'' + ", message='" + message + '\'' + ", reply='" + reply + '\'' + '}';
    }
}
