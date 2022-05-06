package dtos;


import utils.types.Faction;
import utils.types.Requirements;

public class RequirementsDTO {
    private FactionDTO faction;

    public RequirementsDTO(Requirements r){
        this.faction = new FactionDTO(r.getFaction());
    }

}
