package com.work.team5.handler;

import com.work.team5.service.UserProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = Logger.getLogger(CustomAuthenticationSuccessHandler.class.getName());

    private final UserProfileService userProfileService;

    public CustomAuthenticationSuccessHandler(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String userId = authentication.getName();
        if (userProfileService.hasUserProfile(userId)) {
            response.sendRedirect("/users/home");
        } else {
            response.sendRedirect("/profile/create");
        }
        logger.info("Login successful for user: " + userId);
    }
}
