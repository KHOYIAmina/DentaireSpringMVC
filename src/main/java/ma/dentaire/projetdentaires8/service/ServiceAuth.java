package ma.dentaire.projetdentaires8.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ma.dentaire.projetdentaires8.model.personne.Dentiste;
import ma.dentaire.projetdentaires8.repository.IDaoDentiste;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServiceAuth implements IServiceAuth{
    IDaoDentiste daoDentiste;

    @Override
    public Dentiste loginDentiste(String login, String password, boolean resteConnecter, HttpServletResponse response) {
        Dentiste dentiste = daoDentiste.findDentisteByEmailAndPassword(login, password);
        if (dentiste != null) {
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession(true);
            session.setAttribute("dentiste", dentiste);
            if (resteConnecter)
                response.addCookie(createSessionCookie(session.getId(), 30 * 24 * 60 * 60)); // 30 jours en secondes
            else response.addCookie(createSessionCookie(session.getId(), 0));
        }
        return dentiste;
    }
    private Cookie createSessionCookie(String sessionId, int maxAge) {
        Cookie sessionCookie = new Cookie("session_id", sessionId);
        sessionCookie.setMaxAge(maxAge);
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
