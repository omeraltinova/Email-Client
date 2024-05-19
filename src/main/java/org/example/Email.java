package org.example;

import java.util.List;

public class Email {
    private String subject;
    private String from;
    private String contentType;
    private String content;
    private List<Email> multipartContent;
    private String attachment;

    // Getters and setters

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Email> getMultipartContent() {
        return multipartContent;
    }

    public void setMultipartContent(List<Email> multipartContent) {
        this.multipartContent = multipartContent;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}

