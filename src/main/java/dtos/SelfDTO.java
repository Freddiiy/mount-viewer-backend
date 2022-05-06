package dtos;

import utils.types.Self;

public class SelfDTO {
    private String href;

    public SelfDTO(Self s){
        this.href = s.getHref();
    }

    public String getHref() { return href; }
    public void setHref(String value) { this.href = value; }
}
