package ua.dokat.entity.json.http;

public record HttpRequestInfo(String url, String params) {

    @Override
    public String url() {
        return url;
    }

    @Override
    public String params() {
        return params;
    }

    public String getAbsoluteUrl() {
        return url + params;
    }
}
