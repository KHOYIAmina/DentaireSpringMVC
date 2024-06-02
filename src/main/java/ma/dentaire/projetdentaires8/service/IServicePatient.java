package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.dto.PatientDto;
import ma.dentaire.projetdentaires8.dto.PatientsTableDto;
import ma.dentaire.projetdentaires8.exception.DentaireException;
import ma.dentaire.projetdentaires8.model.personne.Patient;

import java.util.List;

public interface IServicePatient {
    Patient savePatientWithDossierMedical(Patient patient);
    List<PatientDto> findAllPatient();
    Patient ModfierPatient(Patient patient) throws DentaireException;
    void supprimerPatient(Patient patient) throws DentaireException;
    Patient findPatientById(int id);
    List<PatientsTableDto> findPatientsTableList();
}
