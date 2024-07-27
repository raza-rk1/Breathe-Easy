package breatheasy.airqualityindexservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aqi")
public class TestController {

    @GetMapping("/say-hello")
    public String getSecureResponse(){
        return "Hello All!!\nThis is secure response from air quality index service.";
    }

}
