package ma.dentaire.projetdentaires8.dto;

import ma.dentaire.projetdentaires8.model.personne.Dentiste;
import ma.dentaire.projetdentaires8.model.personne.Patient;

import java.time.LocalDateTime;

public record DossierMedicalDto(LocalDateTime dateCreation, Dentiste dentiste, Patient patient) {

}
