package umc.spring.ringleader.search.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PostSearchResultReq {

    public PostSearchResultReq() {
    }

    private String title;
    private String category;
    private String address;
    private String roadAddress;
    private int mapx;
    private int mapy;
    private int regionId;

    @Builder
    public PostSearchResultReq(String title, String category, String address, String roadAddress, int mapx, int mapy) {
        this.title = title;
        this.category = category;
        this.address = address;
        this.roadAddress = roadAddress;
        this.mapx = mapx;
        this.mapy = mapy;
    }

}
