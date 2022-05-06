package dtos;

import utils.types.CreatureDisplay;

import java.util.ArrayList;
import java.util.List;

public class CreatureDisplayDTO {
    private SelfDTO key;
    private long id;

    public CreatureDisplayDTO(CreatureDisplay cd){
        this.key = new SelfDTO(cd.getKey());
        this.id = cd.getID();
    }

    public static List<CreatureDisplayDTO> getDtos(List<CreatureDisplay> cds){
        List<CreatureDisplayDTO> cd_dtos = new ArrayList();
        cds.forEach(cd->cd_dtos.add(new CreatureDisplayDTO(cd)));
        return cd_dtos;
    }

    public SelfDTO getKey() { return key; }
    public void setKey(SelfDTO value) { this.key = value; }

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }
}
