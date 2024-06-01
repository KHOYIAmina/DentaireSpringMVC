package ma.dentaire.projetdentaires8.dto;

import ma.dentaire.projetdentaires8.model.enums.Status;

import java.time.LocalDateTime;

public record FactureShowDto(int nFacture, Status Etat, Double Total, LocalDateTime dateCreation) {
}
