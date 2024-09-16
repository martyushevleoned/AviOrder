package org.example.model.constant;

public enum Page implements Url {
    EDIT_ORDER("/order/edit", "editOrder"),
    VIEW_ORDER("/order/view", "viewOrder"),
    LOGIN("/login", "login"),
    REGISTRATION("/registration", "registration"),
    ACCOUNT("/account", "account"),
    HOME("/", "home"),
    ADMIN("/admin", "admin");

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
