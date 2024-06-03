package ma.dentaire.projetdentaires8.model.operation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.dentaire.projetdentaires8.model.personne.Dentiste;
import ma.dentaire.projetdentaires8.model.personne.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DossierMedicale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dateCreation;

    @ManyToOne
    @JoinColumn(name = "dentiste_id")
    private Dentiste dentiste;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToMany(mappedBy = "dossierMedicale", fetch = FetchType.EAGER)
    private List<Consultation> consultations;
}
