package dtos;


import java.util.List;

public class CharacterDTO {
    private LinksDTO links;
    private List<MountElementDTO> mounts;

    public LinksDTO getLinks() { return links; }
    public void setLinks(LinksDTO value) { this.links = value; }

    public List<MountElementDTO> getMounts() { return mounts; }
    public void setMounts(List<MountElementDTO> value) { this.mounts = value; }

}
