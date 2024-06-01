package ma.dentaire.projetdentaires8.repository;

import ma.dentaire.projetdentaires8.model.operation.Acte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDaoActe extends JpaRepository<Acte, Integer> {
    Acte findByNomAndDent(String nom, int dent);
}
