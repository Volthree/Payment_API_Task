package vladislavmaltsev.paymenttaskapi.component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import vladislavmaltsev.paymenttaskapi.repository.UserPaymentRepository;

import java.io.IOException;

@Component
public class CustomAuth extends SimpleUrlAuthenticationSuccessHandler {
    private final UserPaymentRepository userPaymentRepository;
    private final JwtTokenProvider jwtTokenProvider;
    public CustomAuth(UserPaymentRepository userPaymentRepository,
                      JwtTokenProvider jwtTokenProvider) {
        this.userPaymentRepository = userPaymentRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("Enter customAuth");
//        request.getHeaderNames().asIterator().forEachRemaining(System.out::println);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = getUserIdFromUserDetails(userDetails);
        String redirectUrl = determineTargetUrl(userId);
        String generatedToken = jwtTokenProvider.generateToken(authentication);
        System.out.println("Token - " + generatedToken);
        response.setHeader("Authorization", "Bearer " + generatedToken);
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
//        super.onAuthenticationSuccess(request, response, authentication);
        System.out.println("End customAuth");
    }

    public Long getUserIdFromUserDetails(UserDetails userDetails) {
        return userPaymentRepository.findById(Long.parseLong(userDetails.getUsername())).orElseThrow().getId();
    }

    private String determineTargetUrl(Long userId) {
        return "/payment/" + userId;
    }
}
