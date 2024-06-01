package ma.dentaire.projetdentaires8.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IDaoPayment extends JpaRepository<Payment, Integer> {
}