package ma.dentaire.projetdentaires8.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ma.dentaire.projetdentaires8.dto.PatientsTableDto;
import ma.dentaire.projetdentaires8.model.personne.Dentiste;
import ma.dentaire.projetdentaires8.service.IServiceConsultation;
import ma.dentaire.projetdentaires8.service.IServiceFacture;
import ma.dentaire.projetdentaires8.service.IServicePatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private IServicePatient servicePatient;

    @Autowired
    private IServiceFacture serviceFacture;

    @Autowired
    private IServiceConsultation serviceConsultation;

    @GetMapping("/")
    public String getDashboard(HttpServletRequest request, Model model) {
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("dentiste") == null) {
//            return "redirect:/login";
//        }

        List<PatientsTableDto> patients = servicePatient.findPatientsTableListSorted();


        model.addAttribute("totalPaye", serviceFacture.totalSumFacturesPaye());
        model.addAttribute("totalNonPaye", serviceFacture.totalSumFacturesNonPaye());
        model.addAttribute("numPatients", servicePatient.countAllPatients());
        model.addAttribute("numConsultations", serviceConsultation.countConsultationsCreatedToday());

        model.addAttribute("activePage", "dashboard");
//        Dentiste dentiste = (Dentiste) session.getAttribute("dentiste");
//        model.addAttribute("dentiste", dentiste);
        model.addAttribute("patients", patients);
        return "pages/dashboard";
    }
}
