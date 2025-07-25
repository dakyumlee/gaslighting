package com.gaslab.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class DialogueLog {

    @Id
    @GeneratedValue
    private Long id;

    private String speaker;
    private String content;

    public DialogueLog() {
    }

    public DialogueLog(String speaker, String content) {
        this.speaker = speaker;
        this.content = content;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
