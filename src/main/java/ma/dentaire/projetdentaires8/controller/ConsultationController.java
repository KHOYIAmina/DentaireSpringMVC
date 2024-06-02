package ma.dentaire.projetdentaires8.controller;

import ma.dentaire.projetdentaires8.model.personne.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ConsultationController {
    @GetMapping("/patient/consultation/{id}")
    public String showAddPatientForm(Model model, @PathVariable Integer id) {
        model.addAttribute("activePage", "patients");
        System.out.println(id);
        return "pages/patient/consultation";
    }
}
