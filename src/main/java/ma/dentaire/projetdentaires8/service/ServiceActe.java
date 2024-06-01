package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.model.operation.Acte;
import ma.dentaire.projetdentaires8.repository.IDaoActe;

public class ServiceActe implements IServiceActe{

    IDaoActe daoActe;
    @Override
    public Acte findActeByNomAndDent(String nomActe, int dentActe) {
        Acte acte = daoActe.findByNomAndDent(nomActe, dentActe);
        return acte ;

    }
}
