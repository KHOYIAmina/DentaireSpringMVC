package ma.dentaire.projetdentaires8.model.personne;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import ma.dentaire.projetdentaires8.model.operation.DossierMedicale;

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

    @OneToMany(mappedBy = "dentiste", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DossierMedicale> dossiersMedicaux;
}
