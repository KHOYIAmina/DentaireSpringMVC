package ma.dentaire.projetdentaires8.service;

import jakarta.servlet.http.HttpServletResponse;
import ma.dentaire.projetdentaires8.model.personne.Dentiste;

public interface IServiceAuth {
    Dentiste loginDentiste(String login, String password, boolean resteConnecter, HttpServletResponse response);
    void logout(HttpServletResponse response);
}
