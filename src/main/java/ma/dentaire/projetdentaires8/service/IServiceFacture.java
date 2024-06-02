package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.dto.FactureAddDto;
import ma.dentaire.projetdentaires8.dto.FactureShowDto;
import ma.dentaire.projetdentaires8.dto.FactureUpdateDto;
import ma.dentaire.projetdentaires8.model.comptabilite.Facture;

import java.util.List;

public interface IServiceFacture {
    List<FactureShowDto> findFactures();
    public List<FactureShowDto> findFacturesbyPatient(Long patientId);
    Facture addFacture(FactureAddDto facture);
    Facture updateFacture(FactureUpdateDto factureUpdate);
    void deleteFacture(int factureId);
}
