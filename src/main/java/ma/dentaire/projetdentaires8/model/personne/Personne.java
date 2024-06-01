package ma.dentaire.projetdentaires8.model.personne;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.dentaire.projetdentaires8.model.enums.Sexe;


import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Veuillez saisir le nom de patient")
    private String nom;
    @NotEmpty(message = "Veuillez saisir le prenom de patient")
    private String prenom;
    @NotEmpty(message = "Veuillez saisir la numero de téléphone de patient")
    @Size(min=10, message = "Numero de telephone est invalide")
    private String numTel;
    private String adresse, cin;
    @Size(min=10, message = "Le passport est invalide")
    private String passport;
    private Sexe sexe;
    private LocalDate dateNaissance;
}
