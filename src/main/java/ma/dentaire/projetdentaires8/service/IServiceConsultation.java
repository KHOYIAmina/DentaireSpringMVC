package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.dto.ActeAddDto;
import ma.dentaire.projetdentaires8.dto.ConsultationAddDto;
import ma.dentaire.projetdentaires8.dto.ConsultationNPayeDto;
import ma.dentaire.projetdentaires8.dto.ConsultationShowDto;
import ma.dentaire.projetdentaires8.model.operation.Acte;
import ma.dentaire.projetdentaires8.model.operation.Consultation;
import ma.dentaire.projetdentaires8.model.personne.Patient;

import java.util.List;

public interface IServiceConsultation {
    List<ConsultationShowDto> findConsultations();
    Consultation findByConsultationId(Integer consultationId);
    Consultation AjouterConsultation(ConsultationAddDto consultation, Long idPatient, ActeAddDto acte, Double prixPatient);
    void SupprimerConsultation(Integer consultationId);

    List<ConsultationShowDto> findPatientConsultations(Long id);

    Integer countConsultations();
    Integer countConsultationsCreatedToday();

    Integer countConsultationsByActe(String acte);
    String countConsultationsByActeJsonFormat();
}
