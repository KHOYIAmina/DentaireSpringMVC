package ma.dentaire.projetdentaires8.controller;

import ma.dentaire.projetdentaires8.dto.ActeAddDto;
import ma.dentaire.projetdentaires8.dto.ConsultationAddDto;
import ma.dentaire.projetdentaires8.dto.ConsultationShowDto;
import ma.dentaire.projetdentaires8.dto.PatientInfoDto;
import ma.dentaire.projetdentaires8.model.operation.Acte;
import ma.dentaire.projetdentaires8.model.operation.Consultation;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import ma.dentaire.projetdentaires8.service.IServiceActe;
import ma.dentaire.projetdentaires8.service.IServiceConsultation;
import ma.dentaire.projetdentaires8.service.IServicePatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ConsultationController {

    @Autowired
    private IServicePatient servicePatient;

    @Autowired
    private IServiceConsultation serviceConsultation;

    @Autowired
    private IServiceActe serviceActe;

    @PostMapping("/patient/consultation/{id}")
    public String addPatient(@ModelAttribute ConsultationAddDto consultationAddDto, @PathVariable Integer id) {
        ActeAddDto acteAddDto = new ActeAddDto(consultationAddDto.acte(), consultationAddDto.nDent());
        serviceConsultation.AjouterConsultation(consultationAddDto, id, acteAddDto, consultationAddDto.prixPatient());
        return "redirect:/patient/consultation/" + id;
    }

    @GetMapping("/patient/consultation/{id}")
    public String showAddPatientForm(Model model, @PathVariable Integer id) {
        PatientInfoDto patient = servicePatient.findPatientInfos(id);
        List<ConsultationShowDto> consultations = serviceConsultation.findPatientConsultations(id);
        String actes =  serviceActe.actesJsonFormat();

        ConsultationAddDto consultationAddDto = new ConsultationAddDto(null, null, null,null);


        model.addAttribute("activePage", "patients");
        model.addAttribute("patient", patient);
        model.addAttribute("consultations", consultations);
        model.addAttribute("consultationAdd", consultationAddDto);
        model.addAttribute("actes", actes);
        return "pages/patient/consultation";
    }
}
