package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.dto.*;
import ma.dentaire.projetdentaires8.model.operation.Acte;
import ma.dentaire.projetdentaires8.model.operation.Consultation;
import ma.dentaire.projetdentaires8.model.operation.DossierMedicale;
import ma.dentaire.projetdentaires8.model.operation.InterventionMedecin;
import ma.dentaire.projetdentaires8.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Service
public class ServiceConsultation implements IServiceConsultation{
    @Autowired
    IDaoPatient daoPatient;

    @Autowired
    IDaoDM daoDM;

    @Autowired
    IDaoIM daoIM;

    @Autowired
    IDaoConsultation daoConsultation;

    @Autowired
    IDaoActe daoActe;

    @Override
    public List<ConsultationShowDto> findConsultations(){
        List<Consultation> consultations = daoConsultation.findAll();
        return consultations.stream().map((consultation)-> mapToConsultationDto(consultation)).collect(Collectors.toList());
    }

    @Override
    public Consultation findByConsultationId(Integer consultationId) {
        return daoConsultation.findConsultationById(consultationId);
    }

    public ConsultationShowDto mapToConsultationDto(Consultation consultation) {
        Acte acte = consultation.getActe();
        return new ConsultationShowDto(
                consultation.getId(),
               acte.getNom(),
               acte.getDent(),
               consultation.getPrixConsultation(),
               consultation.getDateCreation().toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        );
    }
    @Override
    public Consultation AjouterConsultation(ConsultationAddDto consultation, Long idPatient, ActeAddDto acte,Double prixPatient) {
        Consultation consultation1 = new Consultation();
        Acte acte1 = daoActe.findByNomAndDent(acte.nom(), acte.dent());
        DossierMedicale dossierMedicale = daoDM.findByPatientId(idPatient);

        consultation1.setNoteMedecin(consultation.noteMedecin());
        consultation1.setDateCreation(LocalDateTime.now());
        consultation1.setDossierMedicale(dossierMedicale);
        consultation1.setActe(acte1);

        if(prixPatient != null) consultation1.setPrixConsultation(prixPatient);
        else consultation1.setPrixConsultation(acte1.getPrix());

        InterventionMedecin interventionMedecin = new InterventionMedecin();
        interventionMedecin.setConsultation(consultation1);
        interventionMedecin.setPrixPatients(prixPatient);

        consultation1.setInterventionMedecin(interventionMedecin);

        return daoConsultation.save(consultation1);
    }

    @Override
    public void SupprimerConsultation(Integer consultationId) {
        if(daoConsultation.findConsultationById(consultationId) != null) {
            daoConsultation.deleteById(consultationId);
        }
    }

    @Override
    public List<ConsultationShowDto> findPatientConsultations(Long id) {
        List<Consultation> consultations= daoConsultation.findConsultationByDossierMedicale(daoDM.findByPatientId(id));
        return consultations.stream().map((consultation)-> mapToConsultationDto(consultation)).collect(Collectors.toList());
    }

    @Override
    public Integer countConsultations() {
        return daoConsultation.countConsultations();
    }

    @Override
    public Integer countConsultationsCreatedToday() {
        return daoConsultation.findConsultationsCreatedToday();
    }

    @Override
    public Integer countConsultationsByActe(String acte) {
        return daoConsultation.countConsultationsByActe_Nom(acte);
    }

    @Override
    public String countConsultationsByActeJsonFormat() {
        var acts = daoActe.findAll();
        var noRepeatActs = acts.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(Acte::getNom))),
                        ArrayList::new));
        String json = "{";

        var j = 0;
        for (Acte act: noRepeatActs) {
            json += "\"" + (act.getNom()) + "\"" + ":" + daoConsultation.countConsultationsByActe_Nom(act.getNom());
            if(j != noRepeatActs.size()-1) json += ",";
            j++;
        }
        json += "}";

        return json;
    }


}
