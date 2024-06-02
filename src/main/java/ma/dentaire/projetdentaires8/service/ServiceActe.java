package ma.dentaire.projetdentaires8.service;

import jakarta.persistence.EntityManager;
import ma.dentaire.projetdentaires8.model.operation.Acte;
import ma.dentaire.projetdentaires8.repository.IDaoActe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceActe implements IServiceActe{
    private EntityManager entityManager;
    IDaoActe daoActe;
    @Override
    public Acte findActeByNomAndDent(String nomActe, int dentActe) {
        Acte acte = daoActe.findByNomAndDent(nomActe, dentActe);
        return acte ;
    }

    @Override
    public List<Integer> findDentsByNom(String nomActe) {
        return daoActe.findActeByNom(nomActe);
    }

}
