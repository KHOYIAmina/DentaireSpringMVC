package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.dto.*;
import ma.dentaire.projetdentaires8.exception.DentaireException;
import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import ma.dentaire.projetdentaires8.model.enums.Status;
import ma.dentaire.projetdentaires8.model.operation.Acte;
import ma.dentaire.projetdentaires8.model.operation.Consultation;
import ma.dentaire.projetdentaires8.repository.IDaoConsultation;
import ma.dentaire.projetdentaires8.repository.IDaoDM;
import ma.dentaire.projetdentaires8.repository.IDaoFacture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ServiceFacture implements IServiceFacture {
    @Autowired
    IDaoFacture daoFacture;
    @Autowired
    IDaoConsultation daoConsultation;
    @Autowired
    IDaoDM daoDM;
    @Override
    public List<FactureShowDto> findFactures() {
        List<Facture> factures = daoFacture.findAll();
        return factures.stream().map(this::mapToFactureDto).collect(Collectors.toList());
    }

    @Override
    public List<FactureShowDto> findFacturesbyPatient(Long patientId) {
        List<Facture> factures = daoFacture.findFacturesByPatient(patientId);
        return factures.stream().map(this::mapToFactureDto).collect(Collectors.toList());
    }

    private FactureShowDto mapToFactureDto(Facture facture) {
        return new FactureShowDto(
                facture.getId(),
                facture.getEtat(),
                facture.getTotal(),
                facture.getDateCreation().toLocalDate()
        );
    }

    @Override
    public Facture addFacture(FactureAddDto factureAdd) {
        List<Consultation> consultations = new ArrayList<>();

        Facture facture = new Facture();
        facture.setDateCreation(LocalDateTime.now());
        facture.setEtat(Status.valueOf(factureAdd.etat()));
        for (Integer id : factureAdd.consultationIds()) {
            Consultation consultation = daoConsultation.findById(id)
                    .orElseThrow(() -> new DentaireException("Consultation not found"));
            consultation.setFacture(daoFacture.save(facture));
            consultations.add(consultation);
        }
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

    @Override
    public Status findStatus(int factureId) {
        return daoFacture.findById(factureId).getEtat();
    }


    @Override
    public List<ConsultationNPayeDto> findPatientConsultations(Long id) {
        List<Consultation> consultations= daoConsultation.findConsultationByDossierMedicale(daoDM.findByPatientId(id));
        List<Consultation> consultationsSansFacture = consultations.stream()
                .filter(consultation -> consultation.getFacture() == null)
                .collect(Collectors.toList());
        return consultationsSansFacture.stream().map(this::mapToConsultationNPayeDto).collect(Collectors.toList());
    }


    public ConsultationNPayeDto mapToConsultationNPayeDto(Consultation consultation){
        Acte acte = consultation.getActe();
        return new ConsultationNPayeDto(
                consultation.getId(),
              acte.getNom(),
              consultation.getDateCreation().toLocalDate()
        );
    }
}
