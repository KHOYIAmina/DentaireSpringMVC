package ma.dentaire.projetdentaires8.service;

import jakarta.persistence.EntityManager;
import ma.dentaire.projetdentaires8.model.operation.Acte;
import ma.dentaire.projetdentaires8.repository.IDaoActe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ServiceActe implements IServiceActe{
    @Autowired
    IDaoActe daoActe;

    @Override
    public Acte findActeByNomAndDent(String nomActe, int dentActe) {
        Acte acte = daoActe.findByNomAndDent(nomActe, dentActe);
        return acte ;
    }

    @Override
    public List<Acte> findDentsByNom(String nomActe) {
        return daoActe.findByNom(nomActe);
    }

    @Override
    public List<Acte> findAllActes() {
        return daoActe.findAll();
    }

    @Override
    public String actesJsonFormat() {
        String jsonActes = "{";
        List<String> actesName = new ArrayList<>();
        List<Acte> actes = daoActe.findAll();
        for (Acte acte : actes) {
            actesName.add(acte.getNom());
        }
        Set<String> setActesName = new HashSet<>(actesName);

        var j = 0;
        for (String acteName : setActesName) {
            jsonActes += "\"" + acteName + "\":[";
            var dents = daoActe.findByNom(acteName);

            var i = 0;
            for (Acte dent: dents){
                jsonActes += dent.getDent();
                if(i != dents.size()-1) jsonActes += ",";
                i++;
            }
            jsonActes += "]";
            if(j != setActesName.size()-1) jsonActes += ",";
            j++;
        }
        jsonActes += "}";
        return jsonActes;
    }


}
