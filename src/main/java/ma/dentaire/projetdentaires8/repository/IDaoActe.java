package ma.dentaire.projetdentaires8.repository;

import ma.dentaire.projetdentaires8.model.operation.Acte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDaoActe extends JpaRepository<Acte, Integer> {
    Acte findByNomAndDent(String nom, int dent);
}
