package org.example.model.constant;

public enum Page implements Url {

    ROOT("/", "root"),
    LOGIN("/login", "login"),
    REGISTRATION("/registration", "registration"),
    PROFILE("/profile", "profile"),
    ORDER("/order","order");

    private final String url;
    private final String template;

    Page(String url, String template) {
        this.url = url;
        this.template = template;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public String getTemplate() {
        return template;
    }
}
