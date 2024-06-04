package ma.dentaire.projetdentaires8.service;

import ma.dentaire.projetdentaires8.dto.*;
import ma.dentaire.projetdentaires8.exception.DentaireException;
import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import ma.dentaire.projetdentaires8.model.enums.Status;
import ma.dentaire.projetdentaires8.model.operation.Acte;
import ma.dentaire.projetdentaires8.model.operation.Consultation;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import ma.dentaire.projetdentaires8.repository.IDaoConsultation;
import ma.dentaire.projetdentaires8.repository.IDaoDM;
import ma.dentaire.projetdentaires8.repository.IDaoFacture;
import ma.dentaire.projetdentaires8.repository.IDaoPatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class ServiceFacture implements IServiceFacture {
    @Autowired
    IDaoFacture daoFacture;
    @Autowired
    IDaoConsultation daoConsultation;
    @Autowired
    IDaoDM daoDM;
    @Autowired
    IDaoPatient daoPatient;

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
    public Facture addFacture(FactureAddDto factureAdd, Patient patient) {

        List<Consultation> consultations = new ArrayList<>();
        Facture facture = new Facture();
        facture.setDateCreation(LocalDateTime.now());
        facture.setEtat(Status.valueOf(factureAdd.etat()));

        double prixPaye = factureAdd.prixPaye() != null ? factureAdd.prixPaye().doubleValue() : 0.0;

        for (Integer id : factureAdd.consultationIds()) {
            Consultation consultation = daoConsultation.findById(id)
                    .orElseThrow(() -> new DentaireException("Consultation not found"));
            consultation.setFacture(daoFacture.save(facture));
            consultations.add(consultation);
        }
        double total = consultations.stream().mapToDouble(Consultation::getPrixConsultation).sum();
        if(factureAdd.prixPaye() == total){
            facture.setEtat(Status.Paye);
        }
        facture.setTotal(total);
        facture.setTotalReste(total - prixPaye);
        facture.setTotalPaye(prixPaye);
        facture.setConsultations(consultations);
        facture.setNom(firstLetterUpperCase(patient.getPrenom()) + " " + firstLetterUpperCase(patient.getNom()));
        return daoFacture.save(facture);
    }

    @Override
    public Facture returnFacture(Integer id) {
        Facture facture = daoFacture.findFactureById(id);
        return facture;
    }

    @Override
    public List<ConsultationNPayeDto> ConsultationFacture(Integer id) {
        Facture facture = daoFacture.findFactureById(id);
        Collection<Consultation> consultations = facture.getConsultations();
        return consultations.stream().map(this::mapToConsultationNPayeDto).collect(Collectors.toList());
    }

    public ConsultationNPayeDto mapConsultationFacture(Consultation consultation){
        Acte acte = consultation.getActe();
        return new ConsultationNPayeDto(
                consultation.getId(),
                acte.getNom(),
                consultation.getDateCreation().toLocalDate(),
                consultation.getPrixConsultation()
        );
    }

    @Override
    public Facture updateFacture(FactureUpdateDto factureUpdate,Integer factureId) {
        Facture existingFacture = daoFacture.findFactureById(factureId);
        if(factureUpdate.etat() == Status.NonPaye){
            if(existingFacture.getTotalReste() >= factureUpdate.prixPaye()){
                existingFacture.setEtat(Status.NonPaye);
                double newTotalPaye = existingFacture.getTotalPaye() + factureUpdate.prixPaye();
                double newTotalReste = existingFacture.getTotal() - newTotalPaye;
                existingFacture.setTotalPaye(newTotalPaye);
                existingFacture.setTotalReste(newTotalReste);
            }
        }
        else{
            existingFacture.setEtat(Status.Paye);
            existingFacture.setTotalPaye(existingFacture.getTotal());
            existingFacture.setTotalReste(0.0);
        }
        existingFacture.setConsultations(existingFacture.getConsultations());
        return daoFacture.save(existingFacture);
    }

    @Override
    public void deleteFacture(Integer factureId) {
        Facture facture = daoFacture.findFactureById(factureId);

        facture.getConsultations().forEach(consultation -> consultation.setFacture(null));
        daoFacture.save(facture);
        daoFacture.delete(facture);
    }

    @Override
    public Status findStatus(int factureId) {
        return daoFacture.findFactureById(factureId).getEtat();
    }


    @Override
    public List<ConsultationNPayeDto> findPatientConsultations(Long id) {
        List<Consultation> consultations= daoConsultation.findConsultationByDossierMedicale(daoDM.findByPatientId(id));
        List<Consultation> consultationsSansFacture = consultations.stream()
                .filter(consultation -> consultation.getFacture() == null)
                .collect(Collectors.toList());
        return consultationsSansFacture.stream().map(this::mapToConsultationNPayeDto).collect(Collectors.toList());
    }

    @Override
    public Integer countFacturesPatient(Long id, Status status) {
        return daoFacture.countFacturesByPatient(id, status);
    }

    @Override
    public Double sumPayeeFacturesPatient(Long id) {
        return daoFacture.sumPayeeFacturesByPatient(id);
    }
    @Override
    public Double sumNonPayeeFacturesPatient(Long id) {
        return daoFacture.sumNonPayeeFacturesByPatient(id);
    }

    @Override
    public Double totalSumFacturesPaye() {
        Double total = 0.0;
        Double patTotal = 0.0;
        for (Patient patient : daoPatient.findAll()) {
            patTotal = sumPayeeFacturesPatient(patient.getId());
            total += patTotal == null ? 0 : patTotal;
        }
        return total;
    }

    @Override
    public Double totalSumFacturesNonPaye() {
        Double total = 0.0;
        Double patTotal = 0.0;
        for (Patient patient : daoPatient.findAll()) {
            patTotal = sumNonPayeeFacturesPatient(patient.getId());
            total += patTotal == null ? 0 : patTotal;
        }
        return total;
    }

    @Override
    public List<CaisseDto> findAllFacturesCaisse() {
        List<CaisseDto> caisseDtos = new ArrayList<>();
        List<Facture> factures = daoFacture.findAll();

        for (Facture facture : factures) {
            CaisseDto caisseDto = mapToCaisseDto(facture);
            caisseDtos.add(caisseDto);
        }
        return caisseDtos;
    }

    @Override
    public String totalEarningsByMonthJson() {
        String json = "{";

        var j = 0;
        for (Month month : Month.values()) {
            json += "\"" + (month.getValue()-1) + "\"" + ":" + totalEarningsByMonth(month.getValue());
            if(j != Arrays.stream(Month.values()).toList().size()-1) json += ",";
            j++;
        }
        json += "}";

        return json;
    }

    public Double totalEarningsByMonth(int month){

        LocalDateTime lastDayOfMonthEnd = LocalDate.of(LocalDateTime.now().getYear(), month, 1)
                .with(TemporalAdjusters.lastDayOfMonth())
                .atTime(23, 59, 59, 999999999);
        LocalDateTime firstDayOfMonthStart = LocalDate.of(LocalDateTime.now().getYear(), month, 1).atStartOfDay();

        var factures =  daoFacture.findAllByDateCreationBetween(
                firstDayOfMonthStart,
                lastDayOfMonthEnd
        );
        Double total = 0.0;
        for (Facture facture : factures) {
            total += facture.getTotalPaye();
        }
        return total;
    }


    public CaisseDto mapToCaisseDto(Facture facture) {
        return new CaisseDto(
                facture.getId(),
                facture.getNom(),
                facture.getEtat(),
                facture.getDateCreation().toLocalDate(),
                facture.getTotal(),
                facture.getTotalPaye(),
                facture.getTotalReste()
        );
    }

    public String firstLetterUpperCase(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }



    public ConsultationNPayeDto mapToConsultationNPayeDto(Consultation consultation){
        Acte acte = consultation.getActe();
        return new ConsultationNPayeDto(
                consultation.getId(),
                acte.getNom(),
                consultation.getDateCreation().toLocalDate(),
                consultation.getPrixConsultation()
        );
    }
}
