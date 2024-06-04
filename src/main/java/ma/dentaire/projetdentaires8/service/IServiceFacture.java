package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.dto.*;
import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import ma.dentaire.projetdentaires8.model.enums.Status;
import ma.dentaire.projetdentaires8.model.personne.Patient;

import java.util.List;

public interface IServiceFacture {
    List<FactureShowDto> findFactures();
    List<FactureShowDto> findFacturesbyPatient(Long patientId);
    Facture addFacture(FactureAddDto factureAdd, Patient patient);
    Facture returnFacture(Integer id);
    Facture updateFacture(FactureUpdateDto factureUpdate, Integer factureId);
    void deleteFacture(Integer factureId);
    Status findStatus(int factureId);
    List<ConsultationNPayeDto> findPatientConsultations(Long id) ;
    List<ConsultationNPayeDto> ConsultationFacture(Integer id);
    Integer countFacturesPatient(Long id, Status status);
    Double sumPayeeFacturesPatient(Long id);
    Double sumNonPayeeFacturesPatient(Long id);

    Double totalSumFacturesPaye();
    Double totalSumFacturesNonPaye();

    List<CaisseDto> findAllFacturesCaisse();


    String totalEarningsByMonthJson();
}
