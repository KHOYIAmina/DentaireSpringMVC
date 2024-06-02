package ma.dentaire.projetdentaires8.model.personne;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.dentaire.projetdentaires8.model.enums.Sexe;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Personnel extends Personne{
    private String password, diplome;
    private LocalDate dateRecrutement;

    public Personnel(@NotEmpty(message = "Veuillez saisir le nom de patient") String nom, @NotEmpty(message = "Veuillez saisir le prenom de patient") String prenom, @NotEmpty(message = "Veuillez saisir la numero de téléphone de patient") @Size(min = 10, message = "Numero de telephone est invalide") String numTel, String adresse, String cin, String passport, Sexe sexe, LocalDate dateNaissance, String email, String password, String diplome, LocalDate dateRecrutement) {
        super(nom, prenom, numTel, adresse, cin, passport, sexe, dateNaissance, email);
        this.password = password;
        this.diplome = diplome;
        this.dateRecrutement = dateRecrutement;
    }
}
