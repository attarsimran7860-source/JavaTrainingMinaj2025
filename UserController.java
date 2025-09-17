package com.userinfo.controller;

import java.time.LocalDateTime;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userinfo.config.UserConfig;
import com.userinfo.model.UserInfo;
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

    
    private final UserConfig userConfig;
    private final Environment environment;

    @Autowired
    public UserController(UserConfig userConfig, Environment environment) {
        this.userConfig = userConfig;
        this.environment = environment;
    }

    @GetMapping("/info")
    public UserInfo getUserInfo() {
        String activeProfile = "default";
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length > 0) {
            activeProfile = activeProfiles[0]; // Assuming only one active profile for this scenario
        }

        logger.info("Serving request with active profile: {}", activeProfile);
        logger.info("Configured User Name: {}, Role: {}", userConfig.getName(), userConfig.getRole());

        return new UserInfo(
                userConfig.getName(),
                userConfig.getRole(),
                activeProfile,
                LocalDateTime.now()
        );
    }
}