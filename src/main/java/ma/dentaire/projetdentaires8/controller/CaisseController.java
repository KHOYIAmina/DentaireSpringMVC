package ma.dentaire.projetdentaires8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CaisseController {

    @GetMapping("/caisse")
    public String getCaisse(Model model) {
        model.addAttribute("activePage", "caisse");
        return "pages/caisse";
    }
}
