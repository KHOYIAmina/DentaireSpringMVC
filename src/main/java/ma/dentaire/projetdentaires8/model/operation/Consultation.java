package ma.dentaire.projetdentaires8.model.operation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.dentaire.projetdentaires8.model.comptabilite.Facture;

import java.time.LocalDateTime;

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

    @ManyToOne
    private Acte acte;

    @ManyToOne()
    @JoinColumn(name = "dossier_medicale_id")
    private DossierMedicale dossierMedicale;

    @OneToOne()
    private InterventionMedecin interventionMedecin;

    @ManyToOne()
    private Facture facture;

}
