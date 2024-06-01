package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.dto.PatientDto;
import ma.dentaire.projetdentaires8.exception.DentaireException;
import ma.dentaire.projetdentaires8.model.operation.DossierMedicale;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import ma.dentaire.projetdentaires8.repository.IDaoDM;
import ma.dentaire.projetdentaires8.repository.IDaoPatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicePatient implements IServicePatient{

    @Autowired
    private IDaoPatient daoPatient;

    @Autowired
    private IDaoDM daoDossierMedicale;
    @Override
    public Patient savePatientWithDossierMedical(Patient patient) {
        Patient savedPatient = daoPatient.save(patient);

        DossierMedicale dossierMedicale = new DossierMedicale();
        dossierMedicale.setPatient(savedPatient);
        dossierMedicale.setDateCreation(LocalDateTime.now());

        daoDossierMedicale.save(dossierMedicale);

        savedPatient.setDossierMedicale(dossierMedicale);
        daoPatient.save(savedPatient);
        return savedPatient;
    }
    @Override
    public List<PatientDto> findAllPatient() {
        List<Patient> patients = daoPatient.findAll();
        return patients.stream().map((patient)-> mapToPatientDto(patient)).collect(Collectors.toList());
    }
    public PatientDto mapToPatientDto(Patient patient) {
        return new PatientDto(
                patient.getNom(),
                patient.getPrenom(),
                patient.getSexe(),
                patient.getDateNaissance(),
                patient.getNumTel(),
                patient.getAntecedent(),
                patient.getCin()
        );
    }

    @Override
    public Patient ModfierPatient(Patient patient) throws DentaireException {
        Patient patient1 = daoPatient.findById(patient.getId());
        if (patient1 != null) {
            return daoPatient.save(patient);
        } else {
            throw new DentaireException("Patient not found");
        }
    }

    @Override
    public void supprimerPatient(Patient patient) throws DentaireException {
        Patient patientToDelete = daoPatient.findById(patient.getId());
        if (patientToDelete != null) {
            patientToDelete.getDossierMedicale().setPatient(null);
            daoPatient.delete(patientToDelete);
        } else {
            throw new DentaireException("Patient not found");
        }
    }
    @Override
    public Patient findPatientById(int id){
        Patient patient = daoPatient.findPatientById(id);
        return patient;
    }

}
