package dtos;


import utils.types.CreatureDisplay;
import utils.types.Mount;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

public class MountDTO {
    private LinksDTO links;
    private long id;
    private String name;
    private List<CreatureDisplayDTO> creatureDisplays;
    private String description;

    //Why does source have the Datatype Faction/FactionDTO????
    //Changed DataType from FactionDTO to SourceDTO.
    //Roll it back if Source has to be refactored out.
    private SourceDTO source;
    private FactionDTO faction;
    private RequirementsDTO requirements;

    public MountDTO(Mount m) {
        if(m.getID() != 0)
            this.id = m.getID();

        this.links = new LinksDTO(m.getLinks());

        this.name = m.getName();

        this.creatureDisplays = CreatureDisplayDTO.getDtos(m.getCreatureDisplays());

        this.description = m.getDescription();

        this.source = new SourceDTO(m.getSource());

        this.faction = new FactionDTO(m.getFaction());

        this.requirements = new RequirementsDTO(m.getRequirements());
    }

    public LinksDTO getLinks() { return links; }
    public void setLinks(LinksDTO value) { this.links = value; }

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public List<CreatureDisplayDTO> getCreatureDisplays() { return creatureDisplays; }
    public void setCreatureDisplays(List<CreatureDisplayDTO> value) { this.creatureDisplays = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public SourceDTO getSource() { return source; }
    public void setSource(SourceDTO value) { this.source = value; }

    public FactionDTO getFaction() { return faction; }
    public void setFaction(FactionDTO value) { this.faction = value; }

    public RequirementsDTO getRequirements() { return requirements; }
    public void setRequirements(RequirementsDTO value) { this.requirements = value; }
}
