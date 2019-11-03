package data.api.Enums;

import javax.swing.text.html.ImageView;

public enum ImagesUrl {
    PASSIVE("passive/"),
    SPLASH_ART("champion/splash/"),
    SQUARE("champion/"),
    LOADING_SCREEN("champion/loading/")
    ;

    public String getUrl() {
        return url;
    }

    private final String url;
    private ImagesUrl(String url) {
        this.url = "http://ddragon.leagueoflegends.com/cdn/9.21.1/img/" + url;
    }
}
