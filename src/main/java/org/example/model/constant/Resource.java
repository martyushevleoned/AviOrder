package org.example.model.constant;

public enum Resource implements Url {

    STATIC("/static");


    private final String url;

    Resource(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
