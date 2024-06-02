package ma.dentaire.projetdentaires8.dto;

import ma.dentaire.projetdentaires8.model.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record FactureShowDto(int nFacture, Status etat, Double total, LocalDate dateCreation) {
}
