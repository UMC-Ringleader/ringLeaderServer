package umc.spring.ringleader.search.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class SearchResultRegion {
    private List<String> area = Arrays.asList(
            "도봉구", "노원구", "중랑구", "강북구", "성북구", "동대문구", "광진구", "성동구", "종로구", "은평구", "중구",
            "용산구", "서대문구", "마포구", "강서구", "양천구", "구로구", "영등포구", "동작구", "금천구", "관악구", "서초구",
            "강남구", "송파구", "강동구"
    );
    private List<String> specificArea = Arrays.asList(
            "망원동","목동","구로동","가산동","신림동","봉천동","사당동","방배동","신사동","압구정동","잠실","둔촌동","천호동",
            "성수","행당동","한남동","이태원","명동","익선동","신촌동","쌍문동","청량리동","회기동","이문동",
            "연남동","아현동","수유동","충무로"
    );

    private List<String> specificRoadArea = Arrays.asList(
            "테헤란", "동일로", "을지로", "종로", "대학로", "연서로", "도봉로"
    );

}
