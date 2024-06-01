package ma.dentaire.projetdentaires8.dto;

import ma.dentaire.projetdentaires8.model.enums.Sexe;
import ma.dentaire.projetdentaires8.model.enums.TypeAnte;

import java.time.LocalDate;
import java.util.List;

public record PatientDto(String nom, String prenom, Sexe sexe, LocalDate dateNaissance, String num_tel, List<TypeAnte> antes, String cin){
}
