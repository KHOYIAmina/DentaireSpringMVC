package ma.dentaire.projetdentaires8.repository;

import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import ma.dentaire.projetdentaires8.model.comptabilite.Payment;
import ma.dentaire.projetdentaires8.model.operation.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDaoPayment extends JpaRepository<Payment, Integer> {
}