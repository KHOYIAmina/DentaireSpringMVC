package ma.dentaire.projetdentaires8.service;

import jakarta.servlet.http.HttpSession;
import ma.dentaire.projetdentaires8.model.personne.Dentiste;
import ma.dentaire.projetdentaires8.repository.IDaoDentiste;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServiceAuth implements IServiceAuth{
    IDaoDentiste daoDentiste;
    @Override
    public Dentiste loginDentiste(String login, String password) {
        Dentiste dentiste = daoDentiste.findDentisteByEmailAndPassword(login, password);
        if (dentiste != null) {
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession(true);
            session.setAttribute("dentiste", dentiste);
        }
        return dentiste;
    }
}
