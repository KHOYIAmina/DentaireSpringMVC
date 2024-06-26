package ma.dentaire.projetdentaires8.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ma.dentaire.projetdentaires8.model.personne.Dentiste;
import ma.dentaire.projetdentaires8.repository.IDaoDentiste;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class ServiceAuth implements IServiceAuth {

    private final IDaoDentiste daoDentiste;

    // Constructor injection
    public ServiceAuth(IDaoDentiste daoDentiste)
    {
        this.daoDentiste = daoDentiste;
    }


    @Override
    public Dentiste loginDentiste(String login, String password, HttpServletResponse response) {
        Dentiste dentiste = daoDentiste.findDentisteByEmailAndPassword(login, password);
        if (dentiste != null) {
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession(true);
            session.setAttribute("dentiste", dentiste);
            response.addCookie(createSessionCookie(session.getId(), 30 * 24 * 60 * 60)); // Persistent cookie for 30 days
        }
        return dentiste;
    }

    private Cookie createSessionCookie(String sessionId, int maxAge) {
        Cookie sessionCookie = new Cookie("Dentiste_idCookie", sessionId);
        sessionCookie.setMaxAge(maxAge);
        sessionCookie.setPath("/");
        return sessionCookie;
    }


    @Override
    public void logout(HttpServletResponse response) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession(false);
        if (session != null) {
            session.invalidate();
            response.addCookie(createSessionCookie("", 0));
        }
    }
}
