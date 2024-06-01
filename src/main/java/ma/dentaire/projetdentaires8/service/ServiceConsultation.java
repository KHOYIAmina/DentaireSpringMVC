package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.dto.*;
import ma.dentaire.projetdentaires8.model.operation.Acte;
import ma.dentaire.projetdentaires8.model.operation.Consultation;
import ma.dentaire.projetdentaires8.model.operation.DossierMedicale;
import ma.dentaire.projetdentaires8.model.operation.InterventionMedecin;
import ma.dentaire.projetdentaires8.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceConsultation implements IServiceConsultation{
    IDaoPatient daoPatient;
    IDaoDM daoDM;
    IDaoIM daoIM;
    IDaoConsultation daoConsultation;
    IDaoActe daoActe;

    @Override
    public List<ConsultationShowDto> findConsultation(){
        List<Consultation> consultations = daoConsultation.findAll();
        return consultations.stream().map((consultation)-> mapToConsultationDto(consultation)).collect(Collectors.toList());
    }
    public ConsultationShowDto mapToConsultationDto(Consultation consultation) {
        Acte acte = consultation.getInterventionMedecin().getActe();
        return new ConsultationShowDto(
               acte.getNom(),
               acte.getDent(),
               consultation.getPrixConsultation(),
               consultation.getDateCreation()
        );
    }
    @Override
    public Consultation AjouterConsultation(ConsultationAddDto consultation, int idPatient, ActeAddDto acte,Double prixPatient) {
        Consultation consultation1 = new Consultation();
        consultation1.setNoteMedecin(consultation.noteMedecin());
        consultation1.setDateCreation(LocalDateTime.now());
        DossierMedicale dossierMedicale = daoDM.findByPatientId(idPatient);
        consultation1.setDossierMedicale(dossierMedicale);
        Acte acte1 = daoActe.findByNomAndDent(acte.nom(), acte.dent());
        InterventionMedecin interventionMedecin = new InterventionMedecin();
        interventionMedecin.setActe(acte1);
        if(prixPatient != null) consultation1.setPrixConsultation(prixPatient);
        else consultation1.setPrixConsultation(acte1.getPrix());
        interventionMedecin.setConsultation(consultation1);
        daoIM.save(interventionMedecin);

        consultation1.setInterventionMedecin(interventionMedecin);

        return daoConsultation.save(consultation1);
    }

    @Override
    public List<ConsultationNPayeDto> findConsultationNonPayé() {
        List<Consultation> nonPaidConsultations = daoConsultation.findByFactureIsNull();
        return nonPaidConsultations.stream().map((consultation)-> mapToConsultationNPayeDto(consultation)).collect(Collectors.toList());
    }

    public ConsultationNPayeDto mapToConsultationNPayeDto(Consultation consultation){
        Acte acte = consultation.getInterventionMedecin().getActe();
        return new ConsultationNPayeDto(
                acte.getNom(),
                consultation.getDateCreation()
        );
    }

}
