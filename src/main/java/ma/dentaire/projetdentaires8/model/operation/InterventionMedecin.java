package ma.dentaire.projetdentaires8.model.operation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InterventionMedecin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double prixPatients;

    @ManyToOne
    @JoinColumn(name="acte_id")
    private Acte acte;

    @OneToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;
}
