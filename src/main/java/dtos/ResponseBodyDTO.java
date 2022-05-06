package dtos;

public class ResponseBodyDTO<T> {
    private LinksDTO _links;
    private final T type;

    public ResponseBodyDTO(T type) {
        this.type = type;
    }

    public ResponseBodyDTO(LinksDTO linksDTO, T type) {
        this._links = linksDTO;
        this.type = type;
    }

    public LinksDTO get_links() {
        return _links;
    }

    public void set_links(LinksDTO _links) {
        this._links = _links;
    }

    public T getType() {
        return type;
    }
}
