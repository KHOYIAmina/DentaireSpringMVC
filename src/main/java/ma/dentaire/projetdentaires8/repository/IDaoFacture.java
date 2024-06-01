package ma.dentaire.projetdentaires8.repository;

import ma.dentaire.projetdentaires8.dto.FactureUpdateDto;
import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDaoFacture extends JpaRepository<Facture, Integer> {
    Facture findById(int id);

}
