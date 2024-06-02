package ma.dentaire.projetdentaires8.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ma.dentaire.projetdentaires8.model.enums.Sexe;
import ma.dentaire.projetdentaires8.model.operation.Acte;
import ma.dentaire.projetdentaires8.model.operation.DossierMedicale;
import ma.dentaire.projetdentaires8.model.personne.Dentiste;
import ma.dentaire.projetdentaires8.repository.IDaoActe;
import ma.dentaire.projetdentaires8.repository.IDaoDentiste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class ActeInitializationService implements CommandLineRunner {

    @Autowired
    IDaoActe daoActe;
    @Autowired
    IDaoDentiste daoDentiste;

    @Override
    public void run(String... args) throws Exception {

        /*
        email : dr.saad@gmail.com
        password : khalil123
        */
        // Vérifier si le dentiste existe déjà dans la base de données
        if (daoDentiste.findByEmail("dr.saad@gmail.com") == null) {
            // Si le dentiste n'existe pas, l'ajouter à la base de données
            daoDentiste.save(new Dentiste(
                    "Saad", "Khalil", "0537715169", "HAY RIAD, A côté de café yogorino, Rabat, 10100, Maroc",
                    "AS14533", null, Sexe.HOMME, LocalDate.of(1990, 1, 1),"dr.saad@gmail.com", "khalil123",
                    "Diplômé de la faculté de médecine dentaire de rabat", LocalDate.of(2018, 7, 19),"AS4587254", "chirurgie dentaire"
            ));
        }

        int count = daoActe.coutAll();
        System.out.println(count);
        if(daoActe.coutAll() == 0){


            daoActe.saveAll(Arrays.asList(
                    new Acte("Détartrage", 1, 200.0),
                    new Acte("Détartrage", 3, 250.0),
                    new Acte("Détartrage", 6, 250.0),
                    new Acte("Détartrage", 9, 300.0),
                    new Acte("Détartrage", 10, 450.0),
                    new Acte("Dévitalisation", 1, 400.0),
                    new Acte("Dévitalisation", 3, 350.0),
                    new Acte("Dévitalisation", 5, 450.0),
                    new Acte("Dévitalisation", 8, 260.0),
                    new Acte("Dévitalisation", 12, 320.0),
                    new Acte("Prothèses", 1, 200.0),
                    new Acte("Prothèses", 18, 220.0),
                    new Acte("Prothèses", 20, 230.0),
                    new Acte("Prothèses", 5, 150.0),
                    new Acte("Prothèses", 12, 250.0),
                    new Acte("Extraction", 3, 350.0),
                    new Acte("Extraction", 4, 220.0),
                    new Acte("Extraction", 10, 250.0),
                    new Acte("Extraction", 12, 280.0),
                    new Acte("Extraction", 13, 230.0),
                    new Acte("Plombage", 1, 350.0),
                    new Acte("Plombage", 2, 380.0),
                    new Acte("Plombage", 3, 330.0),
                    new Acte("Plombage", 4, 320.0),
                    new Acte("Plombage", 5, 450.0),
                    new Acte("Chirurgie", 16, 350.0),
                    new Acte("Chirurgie", 18, 380.0),
                    new Acte("Chirurgie", 19, 330.0),
                    new Acte("Chirurgie", 6, 400.0),
                    new Acte("Chirurgie", 9, 480.0),
                    new Acte("Chirurgie", 26, 500.0),
                    new Acte("Chirurgie", 13, 430.0),
                    new Acte("Chirurgie", 15, 520.0)
            ));
        }

        System.out.println("----------All Data saved into Database----------------------");
    }

}