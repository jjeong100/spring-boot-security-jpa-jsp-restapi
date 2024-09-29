package org.rb.sbsec.ajax.sample.controller;

import java.util.List;

import org.rb.sbsec.logic.SampleLogicService;
import org.rb.sbsec.model.FileComment;
import org.rb.sbsec.model.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 
 * 
 * http://localhost:8080/test/
 * ajax.jsp 파일 열림
 */
@Controller
//@RestController
@RequestMapping("/sample")
public class SampleController {

	@Autowired
	SampleLogicService sampleLogicService;
	
    @GetMapping("/")
    public String index(Model model){
    	System.out.println("Sample!");
    	model.addAttribute("msg","test**");
        return "sample";
    }
    
    //http://localhost:8080/sample/savelist?_path=D:/sample
    @GetMapping("/savelist")
    public ResponseEntity<List<FileInfo>> savelist(@RequestParam String _path) throws Exception{
//    	System.out.println("Sample!"+_path);
//    	model.addAttribute("msg","test**");
    	
        return ResponseEntity.ok(sampleLogicService.insertfile(_path));
//    	return ResponseEntity.status(HttpStatus.OK).build();
    }
    
  //http://localhost:8080/sample/getlist
    @GetMapping("/getlist")
    public ResponseEntity<List<FileInfo>> getlist() throws Exception{
//        return ResponseEntity.ok(sampleLogicService.insertfile(_path));
    	return ResponseEntity.ok(sampleLogicService.getFileList());
    }
    
    /**
     * http://localhost:8080/sample/inserFileList
     * @return
     */
    @GetMapping(value = "/insertFileList")
    public ResponseEntity<List<FileComment>> insertFileList() throws Exception {
    	 return ResponseEntity.ok(sampleLogicService.insertfileComment());
    }
    

}