package utils.types;

public class MountElement {
    private MountMount mount;
    private boolean isUseable;
    private Boolean isFavorite;

    public MountMount getMount() { return mount; }
    public void setMount(MountMount value) { this.mount = value; }

    public boolean getIsUseable() { return isUseable; }
    public void setIsUseable(boolean value) { this.isUseable = value; }

    public Boolean getIsFavorite() { return isFavorite; }
    public void setIsFavorite(Boolean value) { this.isFavorite = value; }
}
