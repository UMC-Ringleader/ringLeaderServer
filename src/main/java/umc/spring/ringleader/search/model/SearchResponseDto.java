package umc.spring.ringleader.search.model;

import lombok.*;

@Data
public class SearchResponseDto {

    private Item[] items;

    @Data
    static class Item {
        private String title;
        private String category;
        private String address;
        private String roadAddress;
        private int mapx;
        private int mapy;

    }
}
