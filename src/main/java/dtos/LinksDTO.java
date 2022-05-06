package dtos;

import utils.types.Links;
import utils.types.Self;

public class LinksDTO {
    private SelfDTO self;

    public LinksDTO(Links l){
        this.self = new SelfDTO(l.getSelf());
    }

    public SelfDTO getSelf() { return self; }
    public void setSelf(SelfDTO value) { this.self = value; }
}
