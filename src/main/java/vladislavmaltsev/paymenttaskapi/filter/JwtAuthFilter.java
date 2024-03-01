package vladislavmaltsev.paymenttaskapi.filter;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vladislavmaltsev.paymenttaskapi.service.JwtTokenService;
import vladislavmaltsev.paymenttaskapi.service.UserService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain) throws ServletException, IOException {
        final String authenticationHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;
        if (
                (authenticationHeader == null || !authenticationHeader.startsWith("Bearer "))) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authenticationHeader.substring(7);
        userName = jwtTokenService.getUserNameFromToken(jwt);
        if (userName != null
                && SecurityContextHolder.getContext().getAuthentication() == null
                && !jwtTokenService.isContainsInBlacklist(jwt)) {
            UserDetails userDetails = userService.loadUserByUsername(userName);
            if (jwtTokenService.isTokenValid(jwt, userDetails)) {
                var authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
