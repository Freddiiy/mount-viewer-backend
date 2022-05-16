package dtos;

import java.util.List;
import java.util.Set;

public class ExtendedCharacterDTO extends CharacterDTO {
    private List<AssetsDTO> assets;
    private String region;

    public ExtendedCharacterDTO(int id, String name, int level, GenderDTO gender, FactionDTO faction, RaceDTO race, List<MountElementDTO> mounts, RealmDTO realm, String region, List<AssetsDTO> assets) {
        super(id, name, level, gender, faction, race, mounts, realm);
        this.region = region;
        this.assets = assets;
    }

    public ExtendedCharacterDTO(CharacterDTO characterDTO, String region, List<AssetsDTO> assets) {
        super(characterDTO.getId(), characterDTO.getName(), characterDTO.getLevel(), characterDTO.getGender(), characterDTO.getFaction(), characterDTO.getRace(), characterDTO.getMounts(), characterDTO.getRealm());
        this.region = region;
        this.assets = assets;
    }

    public List<AssetsDTO> getAssets() {
        return assets;
    }

    public void setAssets(List<AssetsDTO> assets) {
        this.assets = assets;
    }
}
