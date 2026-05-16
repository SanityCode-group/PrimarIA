package es.riberadeltajo.primaria_sanitycode.config;

import es.riberadeltajo.primaria_sanitycode.model.entity.Usuario;
import es.riberadeltajo.primaria_sanitycode.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class OAuth2SuccessHandlerTest {
    @InjectMocks
    private OAuth2SuccessHandler handler;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private WhitelistRepository whitelistRepository;

    @Test
    void testUsuarioNoWhitelist() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        Authentication auth = mock(Authentication.class);
        OAuth2User user = mock(OAuth2User.class);

        when(auth.getPrincipal()).thenReturn(user);
        when(user.getAttribute("email")).thenReturn("test@test.com");

        when(whitelistRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.empty());

        when(request.getSession()).thenReturn(session);

        handler.onAuthenticationSuccess(request, response, auth);

        verify(session).invalidate();

        System.out.println("✔ Usuario bloqueado correctamente");
    }

    @Test
    void testWhitelistDenegado() throws Exception {
        System.out.println("\n=== TEST: Whitelist denegado ===");
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        Authentication auth = mock(Authentication.class);
        OAuth2User user = mock(OAuth2User.class);

        when(auth.getPrincipal()).thenReturn(user);
        when(user.getAttribute("email")).thenReturn("test@test.com");
        when(whitelistRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.empty());

        when(request.getSession()).thenReturn(session);

        handler.onAuthenticationSuccess(request, response, auth);

        verify(session).invalidate();
        System.out.println("✔ Sesión invalidada correctamente");
    }

    @Test
    void testUsuarioExistente() throws Exception {
        System.out.println("\n=== TEST: Usuario existente ===");
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Authentication auth = mock(Authentication.class);
        OAuth2User user = mock(OAuth2User.class);

        Usuario existente = new Usuario();
        existente.setEmail("test@test.com");

        when(auth.getPrincipal()).thenReturn(user);
        when(user.getAttribute("email")).thenReturn("test@test.com");
        when(user.getAttribute("name")).thenReturn("Test");
        when(whitelistRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(new es.riberadeltajo.primaria_sanitycode.model.entity.Whitelist()));

        when(usuarioRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(existente));

        handler.onAuthenticationSuccess(request, response, auth);

        verify(usuarioRepository).save(any(Usuario.class));
        System.out.println("✔ Usuario actualizado correctamente");
    }

    @Test
    void testUsuarioNuevo() throws Exception {
        System.out.println("\n=== TEST: Usuario nuevo ===");
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Authentication auth = mock(Authentication.class);
        OAuth2User user = mock(OAuth2User.class);

        when(auth.getPrincipal()).thenReturn(user);
        when(user.getAttribute("email")).thenReturn("new@test.com");
        when(user.getAttribute("name")).thenReturn("New");

        when(whitelistRepository.findByEmail("new@test.com"))
                .thenReturn(Optional.of(new es.riberadeltajo.primaria_sanitycode.model.entity.Whitelist()));

        when(usuarioRepository.findByEmail("new@test.com"))
                .thenReturn(Optional.empty());

        handler.onAuthenticationSuccess(request, response, auth);

        verify(usuarioRepository).save(any(Usuario.class));
        System.out.println("✔ Usuario creado correctamente");
    }
}