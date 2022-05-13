package utils.types;

import dtos.MountDTO;

public class MountElement {
    private MountDTO mount;
    private boolean isUseable;
    private Boolean isFavorite;

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

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }
}
