package ma.dentaire.projetdentaires8.dto;

import ma.dentaire.projetdentaires8.model.enums.Status;

import java.util.List;

public record FactureAddDto(List<Integer> consultationIds, String etat, Double prixPaye) {
}
