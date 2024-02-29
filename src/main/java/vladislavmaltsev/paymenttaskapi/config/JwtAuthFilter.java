package vladislavmaltsev.paymenttaskapi.config;

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
import vladislavmaltsev.paymenttaskapi.service.JwtTotenService;
import vladislavmaltsev.paymenttaskapi.service.UserService;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtTotenService jwtTotenService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain) throws ServletException, IOException {
        final String authenticationHeader = request.getHeader("Authorization");
        System.out.println(request.getRequestURI());
        final String jwt;
        final String userName;
        boolean deleted = false;
        System.out.println("in filter " + authenticationHeader);
        if (authenticationHeader == null || !authenticationHeader.startsWith("Bearer ")) {
            System.out.println("Bad token");
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authenticationHeader.substring(7);

        if(Objects.equals(request.getRequestURI(), "/api/login")){
            jwtTotenService.deleteTokenFromBalskList(jwt);
            deleted = true;
        }
        userName = jwtTotenService.getUserNameFromToken(jwt);
        if (userName != null
                && SecurityContextHolder.getContext().getAuthentication() == null
                && !deleted && !jwtTotenService.isContainsInBlacklist(jwt)) {

            System.out.println("Enter if authContext null && user != null");
            UserDetails userDetails = userService.loadUserByUsername(userName);
            if (jwtTotenService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }


}
