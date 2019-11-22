package data.enums;

public enum ImagesUrl {
    PASSIVE("passive/"),
    SPLASH_ART("champion/splash/"),
    SQUARE("champion/"),
    LOADING_SCREEN("champion/loading/"),
    ITEMS("item/"),
    SPELLS("spell/")
    ;

    public String getUrl() {
        return url;
    }

    private final String url;
    ImagesUrl(String url) {
        this.url = "http://ddragon.leagueoflegends.com/cdn/9.23.1/img/" + url;
    }
}
