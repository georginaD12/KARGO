package org.latexscribe.LatexScribe.domain.dto;

import org.latexscribe.LatexScribe.domain.enums.Role;

public record UsersDto(
        String username,
        String fullName,
        String email,
        Role role) {
}
