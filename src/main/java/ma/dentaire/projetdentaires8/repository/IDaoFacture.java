package ma.dentaire.projetdentaires8.repository;

import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDaoFacture extends JpaRepository<Facture, Integer> {
}
