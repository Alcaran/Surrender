package data.enums;

public enum EndpointEnum {
    SummonerByName(
            "/lol/summoner/v4/summoners/by-name/LeDragonNoir",
            new String[] {} ,
            new String[] {"summonerName"}
    ),
    SummonerById(
            "/lol/summoner/v4/summoners/by-account/{encryptedAccountId}",
            new String[] {} ,
            new String[] {"encryptedAccountId"}
    ),
    SummonerLeague(
            "/lol/league/v4/entries/by-summoner/",
            new String[] {},
            new String[] {"encryptedAccountId"}
    ),
    ChampionMasteries(
            "/lol/champion-mastery/v4/champion-masteries/by-summoner/{encryptedSummonerId}",
            new String[] {} ,
            new String[] {"encryptedSummonerId"}
    ),
    ChampionMasteryByChampion(
            "/lol/champion-mastery/v4/champion-masteries/by-summoner/{encryptedSummonerId}/by-champion/{championId}",
            new String[] {} ,
            new String[] {"encryptedSummonerId", "championId"}
    ),
    FreeChampionRotation("lol/platform/v3/champion-rotations",
            new String[] {},
            new String[] {}
    );

    private final String path;
    private final String[] parameters;
    private final String[] pathParameters;

    EndpointEnum(String path, String[] parameters, String[] pathParameters) {
        this.path = path;
        this.parameters = parameters;
        this.pathParameters = pathParameters;
    }

    public String getPath() { return path; }

    public String[] getParameters() {
        return parameters;
    }

    public String[] getPathParameters() {
        return pathParameters;
    }
}