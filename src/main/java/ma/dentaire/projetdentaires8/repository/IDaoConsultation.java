package ma.dentaire.projetdentaires8.repository;

import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import ma.dentaire.projetdentaires8.model.operation.Consultation;
import ma.dentaire.projetdentaires8.model.operation.DossierMedicale;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IDaoConsultation extends JpaRepository<Consultation, Integer> {
    List<Consultation> findByFactureIsNull();
}
