package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.model.personne.Dentiste;

public interface IServiceAuth {
    Dentiste loginDentiste(String login, String password);
}
