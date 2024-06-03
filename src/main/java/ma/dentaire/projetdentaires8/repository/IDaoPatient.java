package ma.dentaire.projetdentaires8.repository;

import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDaoPatient extends JpaRepository<Patient, Integer> {
    Patient findPatientById(Long id);
    Patient findById(Long id);

    @Query("SELECT COUNT(p) FROM Patient p")
    Integer countAllPatients();

    List<Patient> findPatientsByOrderByDossierMedicale_DateCreationDesc();

    @Query("SELECT p FROM Patient p JOIN p.dossierMedicale dm JOIN dm.consultations c JOIN c.facture f  WHERE f.id = :id")
    Patient findPatientByFactureId(Integer id);
}
