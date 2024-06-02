package ma.dentaire.projetdentaires8.controller;

import ma.dentaire.projetdentaires8.dto.ConsultationShowDto;
import ma.dentaire.projetdentaires8.dto.PatientInfoDto;
import ma.dentaire.projetdentaires8.model.operation.Consultation;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import ma.dentaire.projetdentaires8.service.IServiceConsultation;
import ma.dentaire.projetdentaires8.service.IServicePatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ConsultationController {

    @Autowired
    private IServicePatient servicePatient;

    @Autowired
    private IServiceConsultation serviceConsultation;

    @GetMapping("/patient/consultation/{id}")
    public String showAddPatientForm(Model model, @PathVariable Integer id) {
        PatientInfoDto patient = servicePatient.findPatientInfos(id);
        List<ConsultationShowDto> consultations = serviceConsultation.findPatientConsultations(id) ;

        model.addAttribute("activePage", "patients");
        model.addAttribute("patient", patient);
        model.addAttribute("consultations", consultations);
        return "pages/patient/consultation";
    }
}
