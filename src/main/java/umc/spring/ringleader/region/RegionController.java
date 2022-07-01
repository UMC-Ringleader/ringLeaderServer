package umc.spring.ringleader.region;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;


}
