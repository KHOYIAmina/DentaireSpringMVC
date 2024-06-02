package ma.dentaire.projetdentaires8.model.personne;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.dentaire.projetdentaires8.model.enums.Sexe;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import ma.dentaire.projetdentaires8.model.operation.DossierMedicale;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
public class Dentiste extends Personnel {

    private String numDocteur;
    private String profession;

    public Dentiste(String nom, String prenom, String numTel, String adresse, String cin, String passport, Sexe sexe, LocalDate dateNaissance, String email, String password, String diplome, LocalDate dateRecrutement, String numDocteur, String profession) {
        super(nom, prenom, numTel, adresse, cin, passport, sexe, dateNaissance, email, password, diplome, dateRecrutement);
        this.numDocteur = numDocteur;
        this.profession = profession;
    }


    @OneToMany(mappedBy = "dentiste", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DossierMedicale> dossiersMedicaux;
}
