package ma.dentaire.projetdentaires8.repository;

import ma.dentaire.projetdentaires8.dto.FactureUpdateDto;
import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDaoFacture extends JpaRepository<Facture, Integer> {
    Facture findById(int id);

    @Query("SELECT f FROM Facture f JOIN f.consultations c JOIN c.dossierMedicale d WHERE d.patient.id = :patientId")
    List<Facture> findFacturesByPatient(@Param("patientId") Long patientId);

}
