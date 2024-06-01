package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.dto.FactureAddDto;
import ma.dentaire.projetdentaires8.dto.FactureShowDto;
import ma.dentaire.projetdentaires8.exception.DentaireException;
import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import ma.dentaire.projetdentaires8.model.comptabilite.Payment;
import ma.dentaire.projetdentaires8.model.enums.Status;
import ma.dentaire.projetdentaires8.model.operation.Consultation;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import ma.dentaire.projetdentaires8.repository.IDaoConsultation;
import ma.dentaire.projetdentaires8.repository.IDaoFacture;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceFacture implements IServiceFacture {
    IDaoFacture daoFacture;
    IDaoConsultation daoConsultation;

    @Override
    public List<FactureShowDto> findFacture() {
        List<Facture> factures = daoFacture.findAll();
        return factures.stream().map((facture)-> mapToFactureDto(facture)).collect(Collectors.toList());
    }
    public FactureShowDto mapToFactureDto(Facture facture) {
        return new FactureShowDto(
                facture.getId(),
                facture.getEtat(),
                facture.getTotal(),
                facture.getDateCreation()
        );
    }

    @Override
    public Facture addFacture(FactureAddDto factureAdd) {
        List<Consultation> consultations = new ArrayList<>();
        for (Integer id : factureAdd.consultationIds()) {
            consultations.add(daoConsultation.findById(id)
                    .orElseThrow(() -> new DentaireException("Consultation not found")));
        }

        Facture facture = new Facture();
        facture.setDateCreation(LocalDateTime.now());
        facture.setEtat(Status.valueOf(factureAdd.etat()));
        facture.setTotal(consultations.stream().mapToDouble(Consultation::getPrixConsultation).sum());
        facture.setPayment(new ArrayList<>());

        for (Consultation consultation : consultations) {
            Payment payment = new Payment();
            payment.setConsultation(consultation);
            payment.setFacture(facture);
            payment.setPrixPaye(factureAdd.prixPaye());
            payment.setPrixRestant(facture.getTotal() - factureAdd.prixPaye());
            facture.getPayment().add(payment);
        }

        return daoFacture.save(facture);
    }

    @Override
    public Facture updateFacture(Facture facture) {
        return null;
    }

    @Override
    public void deleteFacture(int factureId) {

    }
}
