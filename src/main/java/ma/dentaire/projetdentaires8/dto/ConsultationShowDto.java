package ma.dentaire.projetdentaires8.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ConsultationShowDto(int id, String Acte, int Dent, double prix, String dateAjout) {
}
