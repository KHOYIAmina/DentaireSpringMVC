package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.dto.PatientDto;
import ma.dentaire.projetdentaires8.dto.PatientInfoDto;
import ma.dentaire.projetdentaires8.dto.PatientsTableDto;
import ma.dentaire.projetdentaires8.exception.DentaireException;
import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IServicePatient {
    Patient savePatientWithDossierMedical(Patient patient);
    List<PatientDto> findAllPatient();
    Patient ModfierPatient(Patient patient) throws DentaireException;
    void supprimerPatient(Long patientId) throws DentaireException;
    Patient findPatientById(Long id);
    PatientInfoDto findPatientInfos(Long id);
    List<PatientsTableDto> findPatientsTableList();
    List<PatientsTableDto> findPatientsTableListSorted();

    Integer countAllPatients();

    Patient findPatientByFactureId(Integer id);
}
