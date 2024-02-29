package vladislavmaltsev.paymenttaskapi.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {
    private final JwtTotenService jwtTotenService;
    private final SecurityContextLogoutHandler securityContextLogoutHandler;

    public void logout(HttpServletRequest request,
                       HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        jwtTotenService.invalidateToken(request.getHeader("Authorization").substring(7));
        securityContextLogoutHandler.logout(request, response, authentication);
    }
}
