package org.example.model.constant;

import org.junit.Assert;
import org.junit.Test;

public class UrlTest {

    private final Url firstUrl = () -> "/some/url";
    private final Url secondUrl = () -> "/another/url";

    @Test
    public void getParamUrl() {
        Assert.assertEquals("/some/url", firstUrl.getParamUrl());
        Assert.assertEquals("/some/url/", firstUrl.getParamUrl(""));
        Assert.assertEquals("/some/url/**", firstUrl.getParamUrl("**"));
        Assert.assertEquals("/some/url/first", firstUrl.getParamUrl("first"));
        Assert.assertEquals("/some/url/first/**", firstUrl.getParamUrl("first", "**"));
        Assert.assertEquals("/some/url/**/second", firstUrl.getParamUrl("**", "second"));
        Assert.assertEquals("/some/url/first/second", firstUrl.getParamUrl("first", "second"));
        Assert.assertEquals("/some/url/first/second/**", firstUrl.getParamUrl("first", "second", "**"));

        Assert.assertEquals("/another/url", secondUrl.getParamUrl());
        Assert.assertEquals("/another/url/**/file", secondUrl.getParamUrl("**", "file"));
    }
}