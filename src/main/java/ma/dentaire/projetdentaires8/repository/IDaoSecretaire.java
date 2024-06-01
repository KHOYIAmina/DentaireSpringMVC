package ma.dentaire.projetdentaires8.repository;

import ma.dentaire.projetdentaires8.model.personne.Dentiste;
import ma.dentaire.projetdentaires8.model.personne.Secretaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDaoSecretaire extends JpaRepository<Secretaire, Integer> {
    Secretaire findSecretaireByEmailAndPassword(String email, String password);

}
