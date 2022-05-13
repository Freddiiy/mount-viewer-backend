package dtos;

import entities.Mount;
import utils.types.MountElement;

public class MountElementDTO {
    private MountDTO mount;
    private boolean isUseable;
    private boolean isFavorite;

    public MountElementDTO(MountDTO mount, boolean isUseable, boolean isFavorite) {
        this.mount = mount;
        this.isUseable = isUseable;
        this.isFavorite = isFavorite;
    }

    public MountDTO getMount() {
        return mount;
    }

    public void setMount(MountDTO mount) {
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
