package SYSC4806.survey.cookies;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CookieFormatDelimiter {
    private static final String DELIMITER = ":";
    private static final int AGE = 7 * 24 * 60 * 60; // 1 week

    /**
     * Centralized way of creating cookies with potentially multiple arguments/values
     *
     * @param name
     * @param arguments
     * @return
     */
    public String formatCookie(String name, List<String> arguments) {
        StringBuilder cookieValue = new StringBuilder();
        for(String s: arguments) {
            cookieValue.append(s).append(DELIMITER);
        }

        return ResponseCookie.from(name, cookieValue.toString())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(AGE) //One week
                .build()
                .toString();
    }

    /**
     * Gets the arguments from a response cookie, that has been encoded using the above format
     * @param cookie
     * @return
     */
    public List<String> decodeCookie(String cookie) {
        if(cookie == null) return new ArrayList<>();
        return Arrays.stream(cookie.split(DELIMITER)).collect(Collectors.toList());
    }

}
