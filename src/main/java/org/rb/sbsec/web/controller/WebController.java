package org.rb.sbsec.web.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 
 * 
 * http://localhost:8080/web/
 * ajax.jsp 파일 열림
 */
@Controller
@RequestMapping("/web")
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
    @GetMapping("/img")
    public ResponseEntity<byte[]> display(@RequestParam("filepath") String filepath) {
    	System.out.println("■ filepath : "+filepath);
    	//파일이 저장된 경로
    	File file = new File(filepath);
    	
    	//저장된 이미지파일의 이진데이터 형식을 구함
    	byte[] result=null;//1. data
    	ResponseEntity<byte[]> entity=null;
    	
    	try {
        	result = FileCopyUtils.copyToByteArray(file);
    		
    		//2. header
    		HttpHeaders header = new HttpHeaders();
    		header.add("Content-type",Files.probeContentType(file.toPath())); //파일의 컨텐츠타입을 직접 구해서 header에 저장
    			
    		//3. 응답본문
    		entity = new ResponseEntity<>(result,header,HttpStatus.OK);//데이터, 헤더, 상태값
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	return entity;
    }
    
}