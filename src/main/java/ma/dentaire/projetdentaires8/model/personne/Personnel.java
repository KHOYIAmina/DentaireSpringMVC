package ma.dentaire.projetdentaires8.model.personne;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Personnel extends Personne{
    private String email, password, diplome;
    private LocalDate dateRecrutement;

}
