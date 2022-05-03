package utils.types;

import java.util.List;

public class Mount {
    private Links links;
    private long id;
    private String name;
    private List<CreatureDisplay> creatureDisplays;
    private String description;
    private Faction source;
    private Faction faction;
    private Requirements requirements;

    public Links getLinks() { return links; }
    public void setLinks(Links value) { this.links = value; }

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public List<CreatureDisplay> getCreatureDisplays() { return creatureDisplays; }
    public void setCreatureDisplays(List<CreatureDisplay> value) { this.creatureDisplays = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public Faction getSource() { return source; }
    public void setSource(Faction value) { this.source = value; }

    public Faction getFaction() { return faction; }
    public void setFaction(Faction value) { this.faction = value; }

    public Requirements getRequirements() { return requirements; }
    public void setRequirements(Requirements value) { this.requirements = value; }
}

class CreatureDisplay {
    private Self key;
    private long id;

    public Self getKey() { return key; }
    public void setKey(Self value) { this.key = value; }

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }
}

class Self {
    private String href;

    public String getHref() { return href; }
    public void setHref(String value) { this.href = value; }
}

class Faction {
    private String type;
    private String name;

    public String getType() { return type; }
    public void setType(String value) { this.type = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }
}

class Links {
    private Self self;

    public Self getSelf() { return self; }
    public void setSelf(Self value) { this.self = value; }
}

class Requirements {
    private Faction faction;
}
