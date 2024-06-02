package ma.dentaire.projetdentaires8.dto;

import ma.dentaire.projetdentaires8.model.enums.Sexe;
import ma.dentaire.projetdentaires8.model.enums.TypeAnte;

import java.time.LocalDate;
import java.util.List;

public record PatientsTableDto(Long id ,String nom, String prenom, Sexe sexe, Integer age, LocalDate dateNaissance, String num_tel, String antes, String cin, int fnp) {
}
