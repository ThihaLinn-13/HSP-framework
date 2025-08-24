package com.hover.project.audit.impl;

import com.hover.project.security.model.JwtUserPrincipal;
import com.sun.security.auth.UserPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;


@Component
public class AuditorAwareImpl implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof JwtUserPrincipal principal) {
            return Optional.of(principal.getId());
        }

        return Optional.empty();
    }
}

