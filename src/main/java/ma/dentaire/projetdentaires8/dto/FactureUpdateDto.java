package ma.dentaire.projetdentaires8.dto;

import ma.dentaire.projetdentaires8.model.enums.Status;

public record FactureUpdateDto (Integer factureId, Status etat, Double prixPaye){
}
