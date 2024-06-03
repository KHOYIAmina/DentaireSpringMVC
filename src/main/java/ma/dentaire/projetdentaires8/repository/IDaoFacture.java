package ma.dentaire.projetdentaires8.repository;

import ma.dentaire.projetdentaires8.dto.FactureUpdateDto;
import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import ma.dentaire.projetdentaires8.model.enums.Status;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDaoFacture extends JpaRepository<Facture, Integer> {
    Facture findById(int id);

    @Query("SELECT f FROM Facture f JOIN f.consultations c JOIN c.dossierMedicale d WHERE d.patient.id = :patientId")
    List<Facture> findFacturesByPatient(@Param("patientId") Long patientId);

    @Query("SELECT COUNT(f) FROM Facture f JOIN f.consultations c JOIN c.dossierMedicale d WHERE d.patient.id = :patientId AND f.etat = :etat")
    Integer countFacturesByPatient(@Param("patientId") Long patientId, @Param("etat") Status etat);

    @Query("SELECT SUM(f.totalPaye) FROM Facture f JOIN f.consultations c JOIN c.dossierMedicale d WHERE d.patient.id = :patientId")
    Double sumPayeeFacturesByPatient(@Param("patientId") Long patientId);

    @Query("SELECT SUM(f.totalReste) FROM Facture f JOIN f.consultations c JOIN c.dossierMedicale d WHERE d.patient.id = :patientId")
    Double sumNonPayeeFacturesByPatient(@Param("patientId") Long patientId);

}
