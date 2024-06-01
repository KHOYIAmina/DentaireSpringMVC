package ma.dentaire.projetdentaires8.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ConsultationShowDto(String Acte, int Dent, double prix, LocalDateTime dateAjout) {
}
