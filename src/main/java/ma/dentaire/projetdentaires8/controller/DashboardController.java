package ma.dentaire.projetdentaires8.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ma.dentaire.projetdentaires8.model.personne.Dentiste;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping("/")
    public String getDashboard(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("dentiste") == null) {
            return "redirect:/login";
        }
        Dentiste dentiste = (Dentiste) session.getAttribute("dentiste");
        model.addAttribute("dentiste", dentiste);
        return "pages/dashboard";
    }

}
