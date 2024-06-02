package ma.dentaire.projetdentaires8.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ma.dentaire.projetdentaires8.model.personne.Dentiste;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("dentiste") == null) {
//            response.sendRedirect("/login");
//            return false;
//        }
        return true;
    }
}
