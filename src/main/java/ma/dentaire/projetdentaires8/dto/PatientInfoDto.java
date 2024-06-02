package ma.dentaire.projetdentaires8.dto;

import ma.dentaire.projetdentaires8.model.enums.Sexe;

import java.time.LocalDate;

public record PatientInfoDto(Long id,String nom, String prenom, Sexe sexe, Integer age, LocalDate dateNaissance, String numTel, String email, String antes, String cin) {
}
