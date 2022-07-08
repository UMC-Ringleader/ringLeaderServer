package umc.spring.ringleader.search.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
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
    private int reviewId;
}
