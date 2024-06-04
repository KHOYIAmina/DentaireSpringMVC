package ma.dentaire.projetdentaires8.controller;

import ma.dentaire.projetdentaires8.service.IServiceConsultation;
import ma.dentaire.projetdentaires8.service.IServiceFacture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CaisseController {

    @Autowired
    IServiceFacture serviceFacture;

    @Autowired
    IServiceConsultation serviceConsultation;

    @GetMapping("/caisse")
    public String getCaisse(Model model) {

        model.addAttribute("monthlyGraph", serviceFacture.totalEarningsByMonthJson());

        model.addAttribute("totalPaye", serviceFacture.totalSumFacturesPaye());
        model.addAttribute("totalNonPaye", serviceFacture.totalSumFacturesNonPaye());

        model.addAttribute("factures", serviceFacture.findAllFacturesCaisse());
        model.addAttribute("activePage", "caisse");
        return "pages/caisse";
    }


}
