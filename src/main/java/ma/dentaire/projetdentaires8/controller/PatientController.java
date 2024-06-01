package ma.dentaire.projetdentaires8.controller;

import ma.dentaire.projetdentaires8.dto.PatientDto;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import ma.dentaire.projetdentaires8.service.IServicePatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PatientController {

    @Autowired
    private IServicePatient servicePatient;

    @GetMapping("/patient")
    public String showAddPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient"; // Correspond au nom de votre fichier HTML sans extension .html
    }

    @PostMapping("/addPatient")
    public String addPatient(@ModelAttribute Patient patient) {
        servicePatient.savePatientWithDossierMedical(patient);
        return "redirect:/patients"; // Redirection vers une page affichant tous les patients ou une page de confirmation
    }

    @GetMapping("/patients")
    public String listPatients(Model model) {
        List<PatientDto> patients = servicePatient.findAllPatient();
        model.addAttribute("patients", patients);
        return "patients"; // Correspond au nom de votre fichier HTML sans extension .html
    }
}
