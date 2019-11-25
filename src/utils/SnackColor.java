package utils;

import javafx.css.PseudoClass;

class SnackColor extends PseudoClass {
    private String classe;
    SnackColor (String classe) {
        this.classe = classe;
    }

    @Override
    public String getPseudoClassName() {
        return classe;
    }
}
