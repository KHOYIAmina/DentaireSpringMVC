package ma.dentaire.projetdentaires8.model.operation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import ma.dentaire.projetdentaires8.model.comptabilite.Payment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Consultation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dateCreation;
    private Double prixConsultation;
    @Column(columnDefinition = "TEXT")
    private String noteMedecin;

    @ManyToOne()
    @JoinColumn(name = "dessier_id")
    private DossierMedicale dossierMedicale;

    @OneToOne(mappedBy = "consultation", cascade = CascadeType.REMOVE)
    private InterventionMedecin interventionMedecin;

    @OneToMany(mappedBy = "consultation")
    private Collection<Payment> payment = new ArrayList<>();
}
