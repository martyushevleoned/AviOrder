package org.example.model.constant;

import java.util.Arrays;

public interface Url {

    String getUrl();

    default String getParamUrl(Object... params) {
        StringBuilder stringBuilder = new StringBuilder(getUrl());
        Arrays.stream(params).sequential().forEach(param -> stringBuilder.append("/").append(param));
        return stringBuilder.toString();
    }

    default String getAnyParamUrl() {
        return getUrl() + "/**";
    }
}
