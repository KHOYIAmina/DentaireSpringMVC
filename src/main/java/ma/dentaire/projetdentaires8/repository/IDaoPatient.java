package ma.dentaire.projetdentaires8.repository;

import ma.dentaire.projetdentaires8.model.personne.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDaoPatient extends JpaRepository<Patient, Integer> {
    Patient findPatientById(int id);
    Patient findById(Long id);
}
