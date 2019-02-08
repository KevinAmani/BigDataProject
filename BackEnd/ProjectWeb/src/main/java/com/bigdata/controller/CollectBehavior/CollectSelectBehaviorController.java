package com.bigdata.controller.CollectBehavior;

import com.bigdata.model.System.Message;
import com.bigdata.model.UserBehavior.*;
import com.bigdata.repository.UserBehavior.SelectBehaviorRepository;
import com.bigdata.service.UserBehavior.SelectBehaviorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CollectSelectBehaviorController {

    @Autowired
    private SelectBehaviorService selectBehaviorService;
    private Behavior behavior;


    @PostMapping("/select_behavior")
    public @ResponseBody String addNewSelectBehavior(@RequestBody Message message){
        behavior = message.getBehavior();
        SelectBehavior selectbehavior = new SelectBehavior();
        selectbehavior.setBehavior(behavior);
        selectBehaviorService.saveSelectBehavior(selectbehavior);

        //what to return
        return "Save";
    }

}
