package ma.dentaire.projetdentaires8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping("/")
    public String getDashboard(Model model) {
        model.addAttribute("activePage", "dashboard");
        return "pages/dashboard";
    }

}
