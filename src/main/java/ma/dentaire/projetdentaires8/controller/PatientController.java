package ma.dentaire.projetdentaires8.controller;

import ma.dentaire.projetdentaires8.dto.PatientDto;
import ma.dentaire.projetdentaires8.dto.PatientsTableDto;
import ma.dentaire.projetdentaires8.model.enums.Mutuelle;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import ma.dentaire.projetdentaires8.service.IServicePatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class PatientController {

    @Autowired
    private IServicePatient servicePatient;

    @PostMapping("/patient/add")
    public String addPatient(@ModelAttribute Patient patient) {
        servicePatient.savePatientWithDossierMedical(patient);
        return "redirect:/patients";
    }

    @GetMapping("/patients")
    public String getPatients(Model model) {
        List<PatientsTableDto> patients = servicePatient.findPatientsTableList();
        Patient patient = new Patient();
        List<Mutuelle> mutuelles= Arrays.stream(Mutuelle.values()).toList();

        model.addAttribute("activePage", "patients");
        model.addAttribute("patients", patients);
        model.addAttribute("patient", patient);
        model.addAttribute("mutuelles", mutuelles);
        return "pages/patients";
    }
}
