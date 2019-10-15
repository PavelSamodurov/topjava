package ru.javawebinar.topjava.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.SecurityUtil;

@Controller
public class ProfileRestController extends AbstractUserController {
    @Autowired
    private SecurityUtil securityUtil;

    public User get() {
        return super.get(securityUtil.authUserId());
    }

    public void delete() {
        super.delete(securityUtil.authUserId());
    }

    public void update(User user) {
        super.update(user, securityUtil.authUserId());
    }
}