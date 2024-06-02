package ma.dentaire.projetdentaires8.controller;

import ma.dentaire.projetdentaires8.dto.ConsultationNPayeDto;
import ma.dentaire.projetdentaires8.dto.FactureAddDto;
import ma.dentaire.projetdentaires8.dto.FactureShowDto;
import ma.dentaire.projetdentaires8.dto.PatientsTableDto;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import ma.dentaire.projetdentaires8.service.IServiceConsultation;
import ma.dentaire.projetdentaires8.service.IServiceFacture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SituationFinanciere {
    @Autowired
    private IServiceConsultation consultationService;
    @Autowired
    private IServiceFacture factureService;
    @GetMapping("/patient/finance/{id}")
    public String showAddPatientForm(Model model, @PathVariable Long id) {
        List<FactureShowDto> factures = factureService.findFacturesbyPatient(id);
        List<ConsultationNPayeDto> consultationsNonPayees = factureService.findPatientConsultations(id);
        model.addAttribute("consultationNP", consultationsNonPayees);
        model.addAttribute("factures", factures);
        model.addAttribute("activePage", "patients");
        return "pages/patient/situationfinanciere";
    }

    @PostMapping("/patient/finance/addFacture")
    public String addFacture(@ModelAttribute FactureAddDto factureAddDto) {
        factureService.addFacture(factureAddDto);
        return "pages/patient/situationfinanciere";
    }


}
