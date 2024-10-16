package org.rb.sbsec.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 * 
 * http://localhost:8080/web/
 * ajax.jsp 파일 열림
 */
@Controller
//@RequestMapping("/web")
public class WebController {

    @GetMapping("/")
//    @ResponseBody
    public String index(Model model){
    	System.out.println("index!");
    	model.addAttribute("msg","test**");
    	
        return "index";
    }
    
    @GetMapping("/sample")
    public String sample(Model model){
    	System.out.println("Sample!");
    	model.addAttribute("msg","test**");
        return "sample";
    }

//    @PostMapping("/send")
//    public String ajaxHome(Model model, SendDto dto){
////        System.out.println("send controller!..");
//        model.addAttribute("msg",dto.getResult());
//        System.out.println(dto.getResult());
//        return "ajax :: #resultDiv";
//    }
    
}