package data.enums;

public enum Servers {
    br1("BR"),
    na1("NA"),
    kr("KR"),
    la1("LA"),
    jp1("JP"),
    tr1("TR"),
    ru("RU"),
    eun1("EU"),
    oc1("OC");

    private String serverName;

    Servers(String serverName) {
        this.serverName = serverName;
    }

    public String toString() {
        return serverName;
    }
}
