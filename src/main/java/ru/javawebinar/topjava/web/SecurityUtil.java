package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

@Component
public class SecurityUtil {
    private static final Logger log = getLogger(SecurityUtil.class);

    private int authUserId = 1;

    public int authUserId() {
        return authUserId;
    }

    public void setAuthUserId(int authUserId) {
        this.authUserId = authUserId;
        log.debug("authUserId:{}",authUserId);
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}