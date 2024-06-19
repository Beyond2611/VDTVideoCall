package org.example.vdtvideocall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @GetMapping("response")
    public String waitResponse(@RequestParam String room){
        return("wait");
    }
    @GetMapping("response/success")
    public String successResponse(){
        return ("success");
    }
    @GetMapping("response/failed")
    public String failedResponse(){
        return ("error");
    }
}
