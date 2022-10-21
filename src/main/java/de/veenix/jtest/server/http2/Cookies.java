package de.veenix.jtest.server.http2;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Cookies {

    private final HashMap<String, String> cookies = new HashMap<>();

    public Cookies(String cookieString) {
        if(cookieString == null) {
            return;
        }

        String[] cookies = cookieString.split(";");
        for(String cookie : cookies) {
            String[] kvPair = cookie.split("=");
            this.cookies.put(URLDecoder.decode(kvPair[0].trim()), kvPair[1].trim());
        }
    }

    public HashMap<String, String> getCookies() {
        return cookies;
    }

    public void addCookie(String key, String value) {
        this.cookies.put(key, value);
    }

    public void addIfNonExistent(String key, Supplier<String> valueFunction) {
        if(!cookies.containsKey(key)) {
            this.cookies.put(key, valueFunction.get());
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        int counter = 0;
        for(Map.Entry<String, String> entry : cookies.entrySet()) {
            if(counter > 0) {
                builder.append(";");
            }
            builder.append(entry.getKey()).append("=").append(entry.getValue());
            counter++;
        }

        return builder.toString();
    }
}
