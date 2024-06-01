package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.dto.FactureAddDto;
import ma.dentaire.projetdentaires8.dto.FactureShowDto;
import ma.dentaire.projetdentaires8.model.comptabilite.Facture;

import java.util.List;

public interface IServiceFacture {
    List<FactureShowDto> findFacture();
    Facture addFacture(FactureAddDto facture);
    Facture updateFacture(Facture facture);
    void deleteFacture(int factureId);
}
