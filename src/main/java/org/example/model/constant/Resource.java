package org.example.model.constant;

public enum Resource implements Url {

    ORDER("/order"),
    SAVE_ORDER("/order/save"),
    DOWNLOAD_ORDER("/order/download"),
    DELETE_ORDER("/order/delete"),
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
