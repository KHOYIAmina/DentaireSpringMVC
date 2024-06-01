package ma.dentaire.projetdentaires8.controller;

import ma.dentaire.projetdentaires8.model.personne.Dentiste;
import ma.dentaire.projetdentaires8.model.personne.Patient;
import ma.dentaire.projetdentaires8.service.ServiceAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class AuthController {

    private ServiceAuth serviceAuth;
    @GetMapping("/login")
    public ModelAndView showAddPatientForm(Model model) {
        return new ModelAndView("login"); // Correspond au nom de votre fichier HTML sans extension .html
        // Correspond au nom de votre fichier HTML sans extension .html
    }
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, @RequestParam(required = false) boolean resteConnecte, HttpServletResponse response) {
        Dentiste dentiste = serviceAuth.loginDentiste(email, password, resteConnecte, response);
        if (dentiste != null) {
            return "Connexion réussie pour " + dentiste.getNom();
        } else {
            return "Échec de la connexion. Veuillez vérifier vos informations de connexion.";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        serviceAuth.logout(response);
        return "Déconnexion réussie.";
    }
}
