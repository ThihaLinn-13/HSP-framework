package com.hover.project.auth.dto;

import java.util.UUID;

public record ChangePasswordRequest(UUID userId, String oldPassword, String newPassword, String confirmPassword) {

}
