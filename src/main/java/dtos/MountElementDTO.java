package dtos;

import utils.types.MountElement;
import utils.types.MountMount;

public class MountElementDTO {
    private MountMountDTO mount;
    private boolean isUseable;
    private boolean isFavorite;

    public MountElementDTO(MountElement me){

        this.mount = new MountMountDTO(me.getMount());
        this.isUseable = me.getIsUseable();
        this.isFavorite = me.getIsFavorite();
    }

    public MountMountDTO getMount() { return mount; }
    public void setMount(MountMountDTO value) { this.mount = value; }

    public boolean getIsUseable() { return isUseable; }
    public void setIsUseable(boolean value) { this.isUseable = value; }

    public Boolean getIsFavorite() { return isFavorite; }
    public void setIsFavorite(Boolean value) { this.isFavorite = value; }
}
