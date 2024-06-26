package ma.dentaire.projetdentaires8.repository;

import ma.dentaire.projetdentaires8.dto.ConsultationShowDto;
import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import ma.dentaire.projetdentaires8.model.operation.Acte;
import ma.dentaire.projetdentaires8.model.operation.Consultation;
import ma.dentaire.projetdentaires8.model.operation.DossierMedicale;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDaoConsultation extends JpaRepository<Consultation, Integer> {
    List<Consultation> findByFactureIsNull();
    List<Consultation> findConsultationByDossierMedicale(DossierMedicale dossierMedicale);
    Consultation findConsultationById(Integer consultationId);

    @Query("SELECT COUNT(c) FROM Consultation c")
    Integer countConsultations();

    @Query("SELECT COUNT(c) FROM Consultation c WHERE FUNCTION('DATE', c.dateCreation) = CURRENT_DATE")
    Integer findConsultationsCreatedToday();

    Integer countConsultationsByActe_Nom(String acte);
}
