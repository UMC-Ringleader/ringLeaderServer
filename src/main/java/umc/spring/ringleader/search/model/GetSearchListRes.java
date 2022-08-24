package umc.spring.ringleader.search.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetSearchListRes {
    private int RRDId;
    private String title;
    private String address;
    private String category;
}
