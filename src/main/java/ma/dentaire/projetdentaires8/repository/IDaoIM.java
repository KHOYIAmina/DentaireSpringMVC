package ma.dentaire.projetdentaires8.repository;

import ma.dentaire.projetdentaires8.model.operation.InterventionMedecin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDaoIM extends JpaRepository<InterventionMedecin, Integer> {
}
