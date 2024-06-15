package org.latexscribe.LatexScribe.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.latexscribe.LatexScribe.domain.enums.Role;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
    private String accessToken;
    private Date accessTokenExpiration;

    private String refreshToken;
    private Date refreshTokenExpiration;

    private Role role;
}
