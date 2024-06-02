package ma.dentaire.projetdentaires8.model.operation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Acte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private int dent;
    private double prix;

    public Acte(String nom, int dent, double prix){
        this.nom = nom;
        this.dent = dent;
        this.prix = prix;
    }
    @OneToMany(mappedBy = "acte", fetch = FetchType.LAZY)
    private Collection<InterventionMedecin> interventionMedecins = new ArrayList<>();
}
