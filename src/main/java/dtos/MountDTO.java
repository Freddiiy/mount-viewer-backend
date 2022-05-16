package dtos;

import entities.Mount;
import errorhandling.NotFoundException;
import utils.types.Source;

import javax.persistence.Basic;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MountDTO {
    private long id;
    private String name;
    private List<String> creatureDisplays;
    private String description;
    private boolean is_useable;

    //Why does source have the Datatype Faction/FactionDTO????
    //Changed DataType from FactionDTO to SourceDTO.
    //Roll it back if Source has to be refactored out.
    private SourceDTO source;
    private FactionDTO faction;
    private RequirementsDTO requirements;


    public MountDTO(Mount m) {
        if(m != null && m.getId() != null) {
            this.id = m.getMountId();
            this.name = m.getName();
            this.is_useable = m.isIs_useable();
            this.creatureDisplays = List.of(m.getDisplay());
            this.description = descriptionPatch(m.getDescription());
            this.source = sourcePatch(new SourceDTO(m.getSource().toUpperCase(), m.getName()));
        } else {
            throw new NullPointerException("Mount is null");
        }

        //this.faction = new FactionDTO(m.getFaction());

       // this.requirements = new RequirementsDTO(m.getRequirements());
    }
    /*
    public LinksDTO getLinks() { return links; }
    public void setLinks(LinksDTO value) { this.links = value; }

     */

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public List<String> getCreatureDisplays() { return creatureDisplays; }
    public void setCreatureDisplays(List<String> value) { this.creatureDisplays = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public SourceDTO getSource() { return source; }
    public void setSource(SourceDTO value) { this.source = value; }

    public FactionDTO getFaction() { return faction; }
    public void setFaction(FactionDTO value) { this.faction = value; }

    public RequirementsDTO getRequirements() { return requirements; }
    public void setRequirements(RequirementsDTO value) { this.requirements = value; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isIs_useable() {
        return is_useable;
    }

    public void setIs_useable(boolean is_useable) {
        this.is_useable = is_useable;
    }

    private String descriptionPatch(String description) {
        if (description == null) {
            return "No description available";
        } else {
            return description;
        }
    }

    private SourceDTO sourcePatch(SourceDTO source) {
        if (source == null) {
            return new SourceDTO(SourceDTO.SourceDTONotAvailable.TYPE, SourceDTO.SourceDTONotAvailable.NAME);
        } else {
            return new SourceDTO(source.getType(), source.getName());
        }
    }
}
