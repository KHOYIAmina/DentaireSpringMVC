package ma.dentaire.projetdentaires8.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import ma.dentaire.projetdentaires8.model.personne.Dentiste;
import ma.dentaire.projetdentaires8.service.IServiceAuth;
import ma.dentaire.projetdentaires8.service.IServicePatient;
import ma.dentaire.projetdentaires8.service.ServiceAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private IServiceAuth serviceAuth;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                        @RequestParam(required = false) boolean resteConnecter,
                        HttpServletResponse response, Model model) {
        Dentiste dentiste = serviceAuth.loginDentiste(email, password, resteConnecter, response);
        if (dentiste != null) {
            return "redirect:/welcome"; // redirect to welcome page on successful login
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login"; // return to login page with error message
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        serviceAuth.logout(response);
        return "redirect:/login"; // redirect to login page after logout
    }
}
