package com.bigdata.controller.CollectBehavior;


import com.bigdata.model.System.Message;
import com.bigdata.model.UserBehavior.*;
import com.bigdata.repository.UserBehavior.UrlBehaviorRepository;
import com.bigdata.service.UserBehavior.UrlBehaviorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CollectUrlBehaviorController {

    @Autowired
    private UrlBehaviorService urlBehaviorService;
    private Behavior behavior;

    @PostMapping("/url_behavior")
    public @ResponseBody String addNewUrlBehavior(@RequestBody Message message){
        behavior = message.getBehavior();
        UrlBehavior urlbehavior = new UrlBehavior();
        urlBehaviorService.saveUrlBehavior(urlbehavior);
        return "Save";
    }


}
