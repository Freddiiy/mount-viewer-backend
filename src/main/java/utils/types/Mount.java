package utils.types;

import java.util.List;
import utils.types.Links;

public class Mount {
    private Links links;
    private long id;
    private String name;
    private List<CreatureDisplay> creatureDisplays;
    private String description;
    private Source source;
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

    public Source getSource() { return source; }
    public void setSource(Source value) { this.source = value; }

    public Faction getFaction() { return faction; }
    public void setFaction(Faction value) { this.faction = value; }

    public Requirements getRequirements() { return requirements; }
    public void setRequirements(Requirements value) { this.requirements = value; }
}
