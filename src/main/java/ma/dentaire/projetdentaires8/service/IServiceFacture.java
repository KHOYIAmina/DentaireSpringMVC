package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.dto.*;
import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import ma.dentaire.projetdentaires8.model.enums.Status;

import java.util.List;

public interface IServiceFacture {
    List<FactureShowDto> findFactures();
    public List<FactureShowDto> findFacturesbyPatient(Long patientId);
    Facture addFacture(FactureAddDto factureAdd);
    Facture updateFacture(FactureUpdateDto factureUpdate);
    void deleteFacture(int factureId);
    Status findStatus(int factureId);
    List<ConsultationNPayeDto> findPatientConsultations(Long id) ;
}
