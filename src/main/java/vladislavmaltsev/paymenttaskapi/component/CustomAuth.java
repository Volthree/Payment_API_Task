package vladislavmaltsev.paymenttaskapi.component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import vladislavmaltsev.paymenttaskapi.repository.UserPaymentRepository;
import vladislavmaltsev.paymenttaskapi.service.JwtTotenService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuth extends SimpleUrlAuthenticationSuccessHandler {
    private final UserPaymentRepository userPaymentRepository;
    private final JwtTotenService jwtTokenService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        System.out.println("Enter customAuth");
//        request.getHeaderNames().asIterator().forEachRemaining(System.out::println);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = getUserIdFromUserDetails(userDetails);
        String redirectUrl = determineTargetUrl(userId);
        String generatedToken = jwtTokenService.generateToken(userDetails);
        System.out.println("Token - " + generatedToken);
        response.setHeader("Authorization", "Bearer " + generatedToken);
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
//        super.onAuthenticationSuccess(request, response, authentication);
        System.out.println("End customAuth with resirect " + redirectUrl);
    }

    public Long getUserIdFromUserDetails(UserDetails userDetails) {
        return userPaymentRepository.findById(Long.parseLong(userDetails.getUsername())).orElseThrow().getId();
    }

    private String determineTargetUrl(Long userId) {
        return "/payment/" + userId;
    }
}
