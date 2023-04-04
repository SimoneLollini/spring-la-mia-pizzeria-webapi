package com.example.spring_la_mia_pizzeria_crud.model;

public class AlertMessage {
    private AlertMessageType type;
    private String text;
    public AlertMessage(AlertMessageType type, String text) {
        this.type = type;
        this.text = text;
    }

    public AlertMessageType getType() {
        return type;
    }

    public void setType(AlertMessageType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public enum AlertMessageType {
        SUCCESS, ERROR
    }
}
