package ma.dentaire.projetdentaires8.controller;

import ma.dentaire.projetdentaires8.model.personne.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLogin(Model model) {

        return "pages/login";
    }

}
