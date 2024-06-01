package ma.dentaire.projetdentaires8.model.personne;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.dentaire.projetdentaires8.model.enums.Mutuelle;
import ma.dentaire.projetdentaires8.model.enums.TypeAnte;
import ma.dentaire.projetdentaires8.model.operation.DossierMedicale;


import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patient extends Personne{
    @Enumerated(EnumType.STRING)
    private Mutuelle mutuelle;
    @ElementCollection(targetClass = TypeAnte.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "patient_antecedents", joinColumns = @JoinColumn(name = "patient_id"))
    @Column(name = "antecedent")
    List<TypeAnte> antecedent;

    @OneToOne(mappedBy = "patient")
    private DossierMedicale dossierMedicale;

}
