package utils.types;
import java.util.List;

public class Character {
    private Links links;
    private List<MountElement> mounts;

    public Links getLinks() { return links; }
    public void setLinks(Links value) { this.links = value; }

    public List<MountElement> getMounts() { return mounts; }
    public void setMounts(List<MountElement> value) { this.mounts = value; }
}
