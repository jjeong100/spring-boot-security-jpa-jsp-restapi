package org.rb.sbsec.ajax.sample.controller;

import org.rb.sbsec.ajax.sample.dto.SendDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
//@RestController
public class AjaxController {

    @GetMapping("/")
    public String index(){
        return "/ajax";
    }

    @PostMapping("/send")
    public String ajaxHome(Model model, SendDto dto){
//    	System.out.println("send controller!..");
        model.addAttribute("msg",dto.getResult());
        System.out.println(dto.getResult());
        return "ajax :: #resultDiv";
    }
}