package ma.dentaire.projetdentaires8.controller;

import ma.dentaire.projetdentaires8.dto.*;
import ma.dentaire.projetdentaires8.exception.DentaireException;
import ma.dentaire.projetdentaires8.model.comptabilite.Facture;
import ma.dentaire.projetdentaires8.model.enums.Status;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import ma.dentaire.projetdentaires8.service.IServiceConsultation;
import ma.dentaire.projetdentaires8.service.IServiceFacture;
import ma.dentaire.projetdentaires8.service.IServicePatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SituationFinanciere {
    @Autowired
    private IServiceConsultation consultationService;
    @Autowired
    private IServiceFacture factureService;

    @Autowired
    private IServicePatient servicePatient;

    @GetMapping("/patient/finance/{id}")
    public String showAddPatientForm(Model model, @PathVariable Long id, @RequestParam(value = "isEdit", required = false) Boolean isEdit, @RequestParam(value ="factureId", required = false) Integer factureId) {
        List<FactureShowDto> factures = factureService.findFacturesbyPatient(id);
        List<ConsultationNPayeDto> consultationsNonPayees = factureService.findPatientConsultations(id);
        Integer coutFacturePayee = factureService.countFacturesPatient(id, Status.Paye);
        Integer coutFactureNonPayee = factureService.countFacturesPatient(id, Status.NonPaye);
        Double sumFacturePayee = factureService.sumPayeeFacturesPatient(id);
        Double sumFactureNonPayee = factureService.sumNonPayeeFacturesPatient(id);
        PatientInfoDto patient = servicePatient.findPatientInfos(id);

        if (isEdit != null && isEdit) {
            Facture factureSelected = factureService.returnFacture(factureId);
            if (factureSelected != null) {
                model.addAttribute("FactureSelected", factureSelected);
                List<ConsultationNPayeDto> consultations = factureService.ConsultationFacture(factureId);
                model.addAttribute("consultations", consultations);
                model.addAttribute("totalPayee", factureSelected.getTotalPaye());
                model.addAttribute("totalReste", factureSelected.getTotalReste());
                model.addAttribute("totalFacture", factureSelected.getTotal());

            } else {
                System.out.println("Facture not found");
            }
        }

        model.addAttribute("consultationNP", consultationsNonPayees);
        model.addAttribute("factures", factures);
        model.addAttribute("factureId", factureId);
        model.addAttribute("patient", patient);
        model.addAttribute("coutFacturePayee", coutFacturePayee);
        model.addAttribute("coutFactureNonPayee", coutFactureNonPayee);
        model.addAttribute("editMode", isEdit);
        model.addAttribute("sumFacturePayee", sumFacturePayee);
        model.addAttribute("sumFactureNonPayee", sumFactureNonPayee);
        model.addAttribute("activePage", "patients");
        return "pages/patient/situationfinanciere";
    }

    @PostMapping("/patient/finance/{id}")
    public String saveFacture(@ModelAttribute FactureAddDto factureAddDto,
                              @ModelAttribute FactureUpdateDto factureUpdateDto,
                              @ModelAttribute("editMode") String editMode,
                              @ModelAttribute("factureId") String factureId,
                              @PathVariable Long id) {
        if (editMode != null && editMode.equalsIgnoreCase("true")) {
            factureService.updateFacture(factureUpdateDto, Integer.parseInt(factureId));
        } else {
            if (factureAddDto.etat().equals("Paye")) {
                double totalPaye = factureAddDto.consultationIds().stream()
                        .mapToDouble(consultationId -> consultationService.findByConsultationId(consultationId)
                                .getPrixConsultation())
                        .sum();
                factureAddDto = new FactureAddDto(factureAddDto.consultationIds(), factureAddDto.etat(), totalPaye);
            }
            factureService.addFacture(factureAddDto, servicePatient.findPatientById(id));
        }
        return "redirect:/patient/finance/" + id;
    }

}
