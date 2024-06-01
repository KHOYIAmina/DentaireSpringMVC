package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.model.operation.Acte;

public interface IServiceActe {
    Acte findActeByNomAndDent(String nomActe, int dentActe);
}
