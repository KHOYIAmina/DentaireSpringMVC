package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.dto.FactureAddDto;
import ma.dentaire.projetdentaires8.dto.FactureShowDto;
import ma.dentaire.projetdentaires8.dto.FactureUpdateDto;
import ma.dentaire.projetdentaires8.exception.DentaireException;
import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import ma.dentaire.projetdentaires8.model.enums.Status;
import ma.dentaire.projetdentaires8.model.operation.Consultation;
import ma.dentaire.projetdentaires8.repository.IDaoConsultation;
import ma.dentaire.projetdentaires8.repository.IDaoFacture;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ServiceFacture implements IServiceFacture {
    IDaoFacture daoFacture;
    IDaoConsultation daoConsultation;

    @Override
    public List<FactureShowDto> findFacture() {
        List<Facture> factures = daoFacture.findAll();
        return factures.stream().map((facture)-> mapToFactureDto(facture)).collect(Collectors.toList());
    }

    @Override
    public List<FactureShowDto> findFacturebyPatient(Long patientId) {
        List<Facture> factures = daoFacture.findFacturesByPatient(patientId);
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
        facture.setTotalPaye(factureAdd.prixPaye());
        facture.setTotalReste(facture.getTotal() - factureAdd.prixPaye());
        facture.setConsultations(consultations);

        return daoFacture.save(facture);
    }

    @Override
    public Facture updateFacture(FactureUpdateDto factureUpdate) {
        Facture existingFacture = daoFacture.findById(factureUpdate.factureId());

        existingFacture.setEtat(factureUpdate.etat());

        double newTotalPaye = existingFacture.getTotalPaye() + factureUpdate.prixPaye();
        double newTotalReste = existingFacture.getTotal() - newTotalPaye;

        existingFacture.setTotalPaye(newTotalPaye);
        existingFacture.setTotalReste(newTotalReste);

        return daoFacture.save(existingFacture);
    }

    @Override
    public void deleteFacture(int factureId) {
        Facture facture = daoFacture.findById(factureId);

        facture.getConsultations().forEach(consultation -> consultation.setFacture(null));
        daoFacture.save(facture);

        daoFacture.delete(facture);
    }

}
