package ma.dentaire.projetdentaires8.repository;

import ma.dentaire.projetdentaires8.dto.DossierMedicalDto;
import ma.dentaire.projetdentaires8.model.operation.DossierMedicale;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDaoDM extends JpaRepository<DossierMedicale, Integer> {
    DossierMedicale findByPatientId(Integer patientId);

}
