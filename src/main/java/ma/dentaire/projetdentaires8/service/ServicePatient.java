package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.dto.PatientDto;
import ma.dentaire.projetdentaires8.dto.PatientInfoDto;
import ma.dentaire.projetdentaires8.dto.PatientsTableDto;
import ma.dentaire.projetdentaires8.exception.DentaireException;
import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import ma.dentaire.projetdentaires8.model.enums.TypeAnte;
import ma.dentaire.projetdentaires8.model.operation.DossierMedicale;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import ma.dentaire.projetdentaires8.repository.IDaoDM;
import ma.dentaire.projetdentaires8.repository.IDaoFacture;
import ma.dentaire.projetdentaires8.repository.IDaoPatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicePatient implements IServicePatient{

    @Autowired
    private IDaoPatient daoPatient;

    @Autowired
    private IDaoDM daoDossierMedicale;

    @Autowired
    private IDaoFacture daoFacture;

    @Override
    public Patient savePatientWithDossierMedical(Patient patient) {
        Patient savedPatient = daoPatient.save(patient);

        DossierMedicale dossierMedicale = new DossierMedicale();
        dossierMedicale.setPatient(savedPatient);
        dossierMedicale.setDateCreation(LocalDateTime.now());

        daoDossierMedicale.save(dossierMedicale);

        savedPatient.setDossierMedicale(dossierMedicale);
        return savedPatient;
    }


    @Override
    public List<PatientDto> findAllPatient() {
        List<Patient> patients = daoPatient.findAll();
        return patients.stream().map((patient)-> mapToPatientDto(patient)).collect(Collectors.toList());
    }

    @Override
    public List<PatientsTableDto> findPatientsTableList() {
        List<Patient> patients = daoPatient.findAll();
        return patients.stream().map((patient)-> mapToPatientsTableDto(patient)).collect(Collectors.toList());
    }

    @Override
    public List<PatientsTableDto> findPatientsTableListSorted() {
        List<Patient> patients = daoPatient.findPatientsByOrderByDossierMedicale_DateCreationDesc();
        return patients.stream().map((patient)-> mapToPatientsTableDto(patient)).collect(Collectors.toList());
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

    public PatientsTableDto mapToPatientsTableDto(Patient patient) {
        Integer age = null;
        if (patient.getDateNaissance() != null)
            age = Period.between(patient.getDateNaissance(), LocalDate.now()).getYears();

        String ant = "Non";
        if (!patient.getAntecedent().isEmpty()) {
            ant = patient.getAntecedent().size() == 1 ? "Oui" : "Plusieur";
        }
        List<Facture> factureList = daoFacture.findFacturesByPatient(patient.getId());

        return new PatientsTableDto(
                patient.getId(),
                patient.getNom(),
                patient.getPrenom(),
                patient.getSexe(),
                age,
                patient.getDateNaissance(),
                patient.getNumTel(),
                ant,
                patient.getCin(),
                factureList.size()
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

    @Override
    public PatientInfoDto findPatientInfos(int id) {
        Patient patient = daoPatient.findPatientById(id);
        return mapToPatientInfo(patient);
    }


    public PatientInfoDto mapToPatientInfo(Patient patient) {
        Integer age = null;
        if (patient.getDateNaissance() != null)
            age = Period.between(patient.getDateNaissance(), LocalDate.now()).getYears();

        String antes = null;
        var antList = patient.getAntecedent();
        if (!antList.isEmpty()) {
            antes = "";
            int i = 0;
            for(TypeAnte ta : antList) {
                antes += ta;
                if (i != antList.size()-1)
                    antes += ", ";
                i++;
            }
        }

        return new PatientInfoDto(
                patient.getId(),
                patient.getNom(),
                patient.getPrenom(),
                patient.getSexe(),
                age,
                patient.getDateNaissance(),
                patient.getNumTel(),
                patient.getEmail(),
                antes,
                patient.getCin()
        );
    }

}
