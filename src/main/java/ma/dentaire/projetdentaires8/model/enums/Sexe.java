package ma.dentaire.projetdentaires8.model.enums;

public enum Sexe {
    FEMME("F"),
    HOMME("H");

    private String abbreviation;

    private Sexe(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
