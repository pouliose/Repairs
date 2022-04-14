package org.Team1.technico.utils;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;

@RequiredArgsConstructor

public class AlgorithmUtil {

    private final Environment env;

    public Algorithm algorUtilMethod() throws NullPointerException{
        return Algorithm.HMAC256(env.getProperty("team1.app.jwtSecret").getBytes());
    }
}
