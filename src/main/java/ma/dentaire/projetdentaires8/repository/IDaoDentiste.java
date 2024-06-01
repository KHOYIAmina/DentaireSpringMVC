package ma.dentaire.projetdentaires8.repository;

import ma.dentaire.projetdentaires8.model.operation.DossierMedicale;
import ma.dentaire.projetdentaires8.model.personne.Dentiste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDaoDentiste extends JpaRepository<Dentiste, Integer> {
    Dentiste findDentisteByEmailAndPassword(String email, String password);
}
