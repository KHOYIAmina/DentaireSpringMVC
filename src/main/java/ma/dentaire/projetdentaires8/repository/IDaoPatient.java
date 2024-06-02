package ma.dentaire.projetdentaires8.repository;

import ma.dentaire.projetdentaires8.model.personne.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDaoPatient extends JpaRepository<Patient, Integer> {
    Patient findPatientById(int id);
    Patient findById(Long id);


    List<Patient> findPatientsByOrderByDossierMedicale_DateCreationDesc();
}
