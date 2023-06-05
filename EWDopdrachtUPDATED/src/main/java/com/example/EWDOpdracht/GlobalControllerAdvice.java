package com.example.EWDOpdracht;

import java.security.Principal;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("username")
    public String getUsername(Principal principal) {
        if (principal != null) {
            return principal.getName();
        }
        return null;
    }
    @ModelAttribute("role")
    public String getRole( Authentication authentication) {
        if (authentication != null) {
            return authentication.getAuthorities().stream().map(x ->x.getAuthority().replaceFirst("ROLE_", "")).collect(Collectors.joining(","));
        }
        return null;
    }
}