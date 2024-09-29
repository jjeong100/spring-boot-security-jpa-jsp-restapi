package org.rb.sbsec.ajax.sample.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.rb.sbsec.ajax.sample.dto.SendDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 
 * 
 * http://localhost:8080/test/
 * ajax.jsp 파일 열림
 */
@Controller
//@RestController
@RequestMapping("/test")
public class AjaxController {

    @GetMapping("/")
    public String index(Model model){
    	System.out.println("Sample!");
    	model.addAttribute("msg","test**");
        return "ajax";
    }

    @PostMapping("/send")
    public String ajaxHome(Model model, SendDto dto){
//        System.out.println("send controller!..");
        model.addAttribute("msg",dto.getResult());
        System.out.println(dto.getResult());
        return "ajax :: #resultDiv";
    }
    
    @GetMapping("/grouping")
    public String grouping(Model model) {
        List<Person> people = Arrays.asList(
                new Person("John", 25),
                new Person("Jane", 30),
                new Person("Bob", 25),
                new Person("Alice", 30)
            );
            Map<Integer, List<Person>> result = people.stream().collect(Collectors.groupingBy(Person::getAge));
            System.out.println(result);
            model.addAttribute("msg",result);
        return "ajax";
    }
}