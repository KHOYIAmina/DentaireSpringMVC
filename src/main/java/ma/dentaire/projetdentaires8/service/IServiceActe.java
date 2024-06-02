package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.model.operation.Acte;

import java.util.List;

public interface IServiceActe {
    Acte findActeByNomAndDent(String nomActe, int dentActe);
    List<Integer> findDentsByNom(String nomActe);
}
