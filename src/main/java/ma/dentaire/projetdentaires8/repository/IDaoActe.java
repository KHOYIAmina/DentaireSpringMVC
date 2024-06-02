package ma.dentaire.projetdentaires8.repository;

import ma.dentaire.projetdentaires8.model.operation.Acte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDaoActe extends JpaRepository<Acte, Integer> {
    Acte findByNomAndDent(String nom, int dent);
    @Query("SELECT a.dent FROM Acte a where a.nom = :nom")
    List<Integer> findActeByNom(@Param("nom") String nom);
    @Query("SELECT COUNT(a) FROM Acte a")
    int coutAll();
}
