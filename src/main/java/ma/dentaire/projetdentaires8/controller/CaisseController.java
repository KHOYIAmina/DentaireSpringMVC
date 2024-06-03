package ma.dentaire.projetdentaires8.controller;

import ma.dentaire.projetdentaires8.service.IServiceFacture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CaisseController {

    @Autowired
    IServiceFacture serviceFacture;

    @GetMapping("/caisse")
    public String getCaisse(Model model) {
        model.addAttribute("factures", serviceFacture.findAllFacturesCaisse());
        model.addAttribute("activePage", "caisse");
        return "pages/caisse";
    }


}
