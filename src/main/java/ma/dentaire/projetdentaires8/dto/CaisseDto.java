package ma.dentaire.projetdentaires8.dto;

import ma.dentaire.projetdentaires8.model.enums.Status;

import java.time.LocalDate;

public record CaisseDto(Integer nFacture, String nomComplet, Status etat, LocalDate dateCreation,
                        Double montant, Double paye, Double reste) {
}
