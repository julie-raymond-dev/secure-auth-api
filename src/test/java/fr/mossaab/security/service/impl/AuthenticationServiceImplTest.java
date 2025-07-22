package fr.mossaab.security.service.impl;

import fr.mossaab.security.entities.User;
import fr.mossaab.security.enums.Role;
import fr.mossaab.security.enums.TokenType;
import fr.mossaab.security.payload.request.AuthenticationRequest;
import fr.mossaab.security.payload.request.RegisterRequest;
import fr.mossaab.security.payload.response.AuthenticationResponse;
import fr.mossaab.security.repository.UserRepository;
import fr.mossaab.security.service.JwtService;
import fr.mossaab.security.service.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link AuthenticationServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private RegisterRequest registerRequest;
    private AuthenticationRequest authenticationRequest;

    @BeforeEach
    void setUp() {
        registerRequest = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .role(Role.USER)
                .build();

        authenticationRequest = AuthenticationRequest.builder()
                .email("john.doe@example.com")
                .password("password")
                .build();
    }

    @Test
    void register_ShouldReturnAuthenticationResponse() {
        User saved = User.builder()
                .id(1L)
                .email(registerRequest.getEmail())
                .password("hashed")
                .role(Role.USER)
                .build();

        when(passwordEncoder.encode(any())).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenReturn(saved);
        when(jwtService.generateToken(saved)).thenReturn("jwt-token");
        when(refreshTokenService.createRefreshToken(saved.getId()))
                .thenReturn(fr.mossaab.security.entities.RefreshToken.builder().token("refresh-token").build());

        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo("jwt-token");
        assertThat(response.getRefreshToken()).isEqualTo("refresh-token");
        assertThat(response.getTokenType()).isEqualTo(TokenType.BEARER.name());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void authenticate_ShouldReturnAuthenticationResponse() {
        User existing = User.builder()
                .id(1L)
                .email(authenticationRequest.getEmail())
                .password("hashed")
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail(existing.getEmail())).thenReturn(Optional.of(existing));
        when(jwtService.generateToken(existing)).thenReturn("jwt-token");
        when(refreshTokenService.createRefreshToken(existing.getId()))
                .thenReturn(fr.mossaab.security.entities.RefreshToken.builder().token("refresh-token").build());

        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo("jwt-token");
        assertThat(response.getRefreshToken()).isEqualTo("refresh-token");
        verify(authenticationManager).authenticate(any());
    }
}
