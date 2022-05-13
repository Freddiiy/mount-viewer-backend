package dtos;


import java.util.List;

public class CharacterDTO {
    private int id;
    private String name;
    private int level;
    private GenderDTO gender;
    private FactionDTO faction;
    private RaceDTO race;
    private List<MountElementDTO> mounts;
    private List<AssetsDTO> media;

    public CharacterDTO(int id, String name, int level, GenderDTO gender, FactionDTO faction, RaceDTO race, List<MountElementDTO> mounts) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.gender = gender;
        this.faction = faction;
        this.race = race;
        this.mounts = mounts;
    }

    public CharacterDTO(CharacterElementDTO characterElement) {
        if (characterElement != null) {
            this.id = characterElement.getId();
            this.name = characterElement.getName();
        }
    }

    public List<MountElementDTO> getMounts() { return mounts; }
    public void setMounts(List<MountElementDTO> value) { this.mounts = value; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public GenderDTO getGender() {
        return gender;
    }

    public void setGender(GenderDTO gender) {
        this.gender = gender;
    }

    public FactionDTO getFaction() {
        return faction;
    }

    public void setFaction(FactionDTO faction) {
        this.faction = faction;
    }

    public RaceDTO getRace() {
        return race;
    }

    public void setRace(RaceDTO race) {
        this.race = race;
    }
}
