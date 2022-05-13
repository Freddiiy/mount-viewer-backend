package dtos;

import entities.Mount;
import utils.types.MountElement;

public class MountElementDTO {
    private BasicMountDTO mount;
    private boolean isUseable;
    private boolean isFavorite;

    public MountElementDTO(BasicMountDTO mount, boolean isUseable, boolean isFavorite) {
        this.mount = mount;
        this.isUseable = isUseable;
        this.isFavorite = isFavorite;
    }

    public BasicMountDTO getMount() {
        return mount;
    }

    public void setMount(BasicMountDTO mount) {
        this.mount = mount;
    }

    public boolean isUseable() {
        return isUseable;
    }

    public void setUseable(boolean useable) {
        isUseable = useable;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}

