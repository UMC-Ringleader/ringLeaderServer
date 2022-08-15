package umc.spring.ringleader.search.model;

import lombok.*;

@Data
public class SearchResponseDto {

    private Item[] items;

    @Data
    public static class Item {
        private String title;
        private String category;
        private String address;
        private String roadAddress;
        private int mapx;
        private int mapy;

        public static PostSearchResultReq itemToDto(SearchResponseDto.Item item) {
            return PostSearchResultReq.builder()
                    .title(item.getTitle())
                    .category(item.getCategory())
                    .address(item.getAddress())
                    .roadAddress(item.getRoadAddress())
                    .mapx(item.getMapx())
                    .mapy(item.getMapy())
                    .build();

        }

    }
}
