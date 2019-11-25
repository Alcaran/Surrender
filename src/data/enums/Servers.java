package data.enums;

public enum Servers {
    br1("BR", "br1"),
    na1("NA", "na1"),
    kr("KR", "kr"),
    la1("LA", "la1"),
    jp1("JP", "jp1"),
    tr1("TR", "tr1"),
    ru("RU", "ru"),
    eun1("EU", "eun1"),
    oc1("OC", "oc1");

    private String serverName;
    private String usefulName;

    Servers(String serverName, String usefulName) {

        this.serverName = serverName;
        this.usefulName = usefulName;
    }

    public String toString() {
        return serverName;
    }

    public String getUsefulName() {
        return usefulName;
    }
}
