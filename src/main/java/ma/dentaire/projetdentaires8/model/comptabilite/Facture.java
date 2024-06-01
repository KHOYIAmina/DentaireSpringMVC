package ma.dentaire.projetdentaires8.model.comptabilite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.dentaire.projetdentaires8.model.enums.Status;
import ma.dentaire.projetdentaires8.model.operation.Consultation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Status etat;
    private LocalDateTime dateCreation;
    private Double total;
    private Double totalPaye, totalReste;


    @OneToMany(mappedBy = "facture", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
    private Collection<Consultation> consultations = new ArrayList<>();
}
