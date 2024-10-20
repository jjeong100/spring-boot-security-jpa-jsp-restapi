/**
 * Spring Boot + JPA/Hibernate + PostgreSQL RESTful CRUD API Example (https://www.dariawan.com)
 * Copyright (C) 2020 Dariawan <hello@dariawan.com>
 *
 * Creative Commons Attribution-ShareAlike 4.0 International License
 *
 * Under this license, you are free to: # Share - copy and redistribute the
 * material in any medium or format # Adapt - remix, transform, and build upon
 * the material for any purpose, even commercially.
 *
 * The licensor cannot revoke these freedoms as long as you follow the license
 * terms.
 *
 * License terms: # Attribution - You must give appropriate credit, provide a
 * link to the license, and indicate if changes were made. You may do so in any
 * reasonable manner, but not in any way that suggests the licensor endorses you
 * or your use. # ShareAlike - If you remix, transform, or build upon the
 * material, you must distribute your contributions under the same license as
 * the original. # No additional restrictions - You may not apply legal terms or
 * technological measures that legally restrict others from doing anything the
 * license permits.
 *
 * Notices: # You do not have to comply with the license for elements of the
 * material in the public domain or where your use is permitted by an applicable
 * exception or limitation. # No warranties are given. The license may not give
 * you all of the permissions necessary for your intended use. For example,
 * other rights such as publicity, privacy, or moral rights may limit how you
 * use the material.
 *
 * You may obtain a copy of the License at
 * https://creativecommons.org/licenses/by-sa/4.0/
 * https://creativecommons.org/licenses/by-sa/4.0/legalcode
 */
package org.rb.sbsec.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.rb.sbsec.exception.BadResourceException;
import org.rb.sbsec.exception.ResourceAlreadyExistsException;
import org.rb.sbsec.exception.ResourceNotFoundException;
import org.rb.sbsec.logic.FileInfoLogicService;
import org.rb.sbsec.model.FileInfo;
import org.rb.sbsec.service.FileInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;

@Api(description = "Endpoints for Creating, Retrieving, Updating and Deleting of Contacts.",
        tags = {"contact"})
@RestController
@RequestMapping("/file")
public class FileInfoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private FileInfoService fileInfoService;
    
    @Autowired
    private FileInfoLogicService fileInfoLogicService;

    @ApiOperation(value = "Find FileInfos by name", notes = "Name search by %name% format", tags = {"fileInfo"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "successful operation", response = List.class)})
    @GetMapping(value = "/fileinfo")
    public ResponseEntity<List<FileInfo>> findAll(
            @ApiParam(name = "fileInfoId",
                    value = "Page number, default is 1",
                    example = "1",
                    required = false) @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @ApiParam("Name of the fileInfo for search.") @RequestParam(required = false) String name) {
        if (StringUtils.isEmpty(name)) {
            return ResponseEntity.ok(fileInfoService.findAll(pageNumber, ROW_PER_PAGE));
        } else {
            return ResponseEntity.ok(fileInfoService.findAllByFileName(name, pageNumber, ROW_PER_PAGE));
        }
    }

    @ApiOperation(value = "Find fileInfo by ID", notes = "Returns a single fileInfo", tags = {"fileInfo"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "successful operation", response = FileInfo.class),
        @ApiResponse(code = 404, message = "FileInfo not found")})
    @GetMapping(value = "/fileinfo/{fileInfoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileInfo> findFileInfoById(
            @ApiParam(name = "fileInfoId",
                    value = "Id of the fileInfo to be obtained. Cannot be empty.",
                    example = "1",
                    required = true)
            @PathVariable long fileInfoId) {
        try {
        	FileInfo fileInfo = fileInfoService.findById(fileInfoId);
            return ResponseEntity.ok(fileInfo);  // return 200, with json body
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @ApiOperation(value = "Add a new fileInfo", tags = {"fileInfo"})
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "FileInfo created"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "FileInfo already exists")})
    @PostMapping(value = "/fileInfos")
    public ResponseEntity<FileInfo> addFileInfo(
            @ApiParam("FileInfo to add. Cannot null or empty.")
            @Valid @RequestBody FileInfo fileInfo)
            throws URISyntaxException {
        try {
        	FileInfo newFileInfo = fileInfoService.save(fileInfo);
            return ResponseEntity.created(new URI("/file/fileInfos/" + newFileInfo.getId()))
                    .body(fileInfo);
        } catch (ResourceAlreadyExistsException ex) {
            // log exception first, then return Conflict (409)
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (BadResourceException ex) {
            // log exception first, then return Bad Request (400)
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Update an existing fileInfo", tags = {"fileInfo"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "successful operation"),
        @ApiResponse(code = 400, message = "Invalid ID supplied"),
        @ApiResponse(code = 404, message = "FileInfo not found"),
        @ApiResponse(code = 405, message = "Validation exception")})
    @PutMapping(value = "/fileinfo/{fileInfoId}")
    public ResponseEntity<FileInfo> updateFileInfo(
            @ApiParam(name = "fileInfoId",
                    value = "Id of the fileInfo to be update. Cannot be empty.",
                    example = "1",
                    required = true)
            @PathVariable long fileInfoId,
            @ApiParam("FileInfo to update. Cannot null or empty.")
            @Valid @RequestBody FileInfo fileInfo) {
        try {
        	fileInfo.setId(fileInfoId);
        	fileInfoService.update(fileInfo);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            // log exception first, then return Not Found (404)
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (BadResourceException ex) {
            // log exception first, then return Bad Request (400)
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

//    @ApiOperation(value = "Update an existing FileInfo's address", tags = {"fileInfo"})
//    @ApiResponses(value = {
//        @ApiResponse(code = 200, message = "successful operation"),
//        @ApiResponse(code = 404, message = "FileInfo not found")})
//    @PatchMapping("/file/{fileInfoId}")
//    public ResponseEntity<Void> updateAddress(
//            @ApiParam(name = "fileInfoId",
//                    value = "Id of the fileInfo to be update. Cannot be empty.",
//                    example = "1",
//                    required = true)
//            @PathVariable long fileInfoId,
//            @ApiParam("fileInfo's address to update.")
//            @RequestBody Address address) {
//        try {
//            fileInfoService.updateAddress(fileInfoId, address);
//            return ResponseEntity.ok().build();
//        } catch (ResourceNotFoundException ex) {
//            // log exception first, then return Not Found (404)
//            logger.error(ex.getMessage());
//            return ResponseEntity.notFound().build();
//        }
//    }

    @ApiOperation(value = "Deletes a FileInfo", tags = {"fileInfo"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "successful operation"),
        @ApiResponse(code = 404, message = "FileInfo not found")})
    @DeleteMapping(path = "/fileinfo/{fileInfoId}")
    public ResponseEntity<Void> deleteFileInfoById(
            @ApiParam(name = "fileInfoId",
                    value = "Id of the fileInfo to be delete. Cannot be empty.",
                    example = "1",
                    required = true)
            @PathVariable long fileInfoId) {
        try {
        	fileInfoService.deleteById(fileInfoId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 
     * @return
     */
    @GetMapping(value = "/readFileList")
    public ResponseEntity<List<FileInfo>> readFileList( @RequestParam("Paging") int Paging
    		                                          , @RequestParam("Count") int Count
    		                                          , @RequestParam("folder") String folder) throws Exception {
    	System.out.println("Paging : "+Paging);
    	System.out.println("Count : "+Count);
    	System.out.println("folder : "+folder);
//    	System.out.println("ibpage : "+ibpage);
    	
//    	String folder = "D:\\torrent\\";
//    	return ResponseEntity.ok(fileInfoLogicService.getFileInfoList(folder));
//    	return ResponseEntity.ok(fileInfoLogicService.getFileInfoList());
//    	return ResponseEntity.ok(fileInfoLogicService.getFileInfoTypeList("F"));
    	return ResponseEntity.ok(fileInfoLogicService.getList(Paging,Count));
    	
    }
    
    @GetMapping(value = "/readFileList1")
    public ResponseEntity<List<FileInfo>> readFileList() throws Exception {
    	return ResponseEntity.ok(fileInfoLogicService.getFileInfoTypeList("F"));
    	
    }
    
    /**
     * http://localhost:8080/file/inserFileList
     * @return
     */
    @PostMapping(value = "/insertFileList")
    public ResponseEntity<List<FileInfo>> insertFileList(@RequestParam("folder") String folder) throws Exception {
    	System.out.println("insertFileList folder : "+folder);
//    	String folder = "D:\\torrent\\";
    	 return ResponseEntity.ok(fileInfoLogicService.insertfile(folder));
    }
    
    @PostMapping(value = "/insertFileBatch")
    public ResponseEntity<List<FileInfo>> insertFileBatch(@RequestParam("folder") String folder) throws Exception {
    	System.out.println("insertFileBatch folder : "+folder);
//    	String folder = "D:\\torrent\\";
    	 return ResponseEntity.ok(fileInfoLogicService.insertFileBatch(folder));
    }
    
    @PostMapping(value = "/delFileList")
    public ResponseEntity<List<FileInfo>> delFileList(@RequestParam("folder") String folder) throws Exception {
    	System.out.println("delFileList folder : "+folder);
//    	String folder = "D:\\torrent\\";
    	 return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    
    /**
     * http://localhost:8080/file/deleteFile?dir=D:\torrent\down\【U6A6.LA】-全網最快國產網紅磁力_16144
     * 
     * aplication.properties에 설정 (특수문자를 파라메터로 받기)
     * server.tomcat.relaxed-query-chars=\\,[,],{,},(,),^,|,"
     * server.tomcat.relaxed-path-chars=\\,[,],{,},(,),^,|,"
     * @param dir
     */
    @GetMapping("/deleteFile")
    public void deleteFile(@RequestParam("dir") String dir) {
    	
//    	String directory ="D:\\torrent\\down\\【U6A6.LA】-全網最快國產網紅磁力_16144";
    	
    	try {
    		String directory = new String(dir.getBytes("8859_1"),"UTF-8");
    		System.out.println("directory : "+directory);
    		
    		List<String> fileRead = fileRead("C:\\workspace\\SpringBoot\\spring-boot-security-jpa-jsp-restapi\\src\\main\\webapp\\temp\\deleteList.txt");
    		
    		List<String> fileInfoList =  fileInfoLogicService.getFileInfoPathList(dir);
    		
    		for(int index=0;index<fileInfoList.size();index++) {
    			if(fileInfoList.get(index).indexOf(".url") != -1) {
    				File isFile = new File(fileInfoList.get(index));
    				if(isFile.isFile()) {
    					isFile.delete();
    				}
    			}
    			
    			for(int i=0;i<fileRead.size();i++) {
    				if(fileInfoList.get(index).indexOf(fileRead.get(i)) != -1) {
    					File isFile = new File(fileInfoList.get(index));
        				if(isFile.isFile()) {
        					System.out.println("delete File:"+fileInfoList.get(index));
        					isFile.delete();
        				}
    				}
    			}
    			
//    			if(fileInfoList.get(index).indexOf(".jpg") != -1)){
//    				fileInfoLogicService.moveFileTo(fileInfoList.get(index));
//    			}
//    			if(fileInfoList.get(index).indexOf(".gif") != -1)){
//    				fileInfoLogicService.moveFileTo(fileInfoList.get(index));
//    			}
    			
    		}
    		
    		for(int index=0;index<fileInfoList.size();index++) {
//    			FileInfo fileInfo = fileInfoList.get(index);
    			System.out.println(fileInfoList.get(index));
    		}
    		
//    		
//    		File temp = new File("D:\\torrent\\down\\【U6A6.LA】-全網最快國產網紅磁力_16146\\文宣\\JAVHD社區\\｜JAVHD_DMM女优资源  ｜.url");
//    		if(temp.isFile()) {
//    		    System.out.println("파일이 존재 합니다.");
//    		    
//    		}else {
//    			System.out.println("파일이 없습니다.");
//    		}
    	}catch(UnsupportedEncodingException e) {
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void deleteDirectiory(String path) {
    	// 폴더와 파일들 삭제
    	File folder = new File(path);

    	try {

    	    if (folder.exists()) {
    	    	FileUtils.cleanDirectory(folder);//하위 폴더와 파일 모두 삭제

	    	    if (folder.isDirectory()) {
	    	      folder.delete(); // 대상폴더 삭제
	    	      System.out.println(folder + "폴더가 삭제되었습니다.");
	    	    }
    	    }
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * 
     * @param path
     */
    public List<String> fileRead(String path) {
    	List<String> result = new ArrayList<String>();
    	try {
	    	BufferedReader reader = new BufferedReader(new FileReader(path)); 
	    	String str;
	    	while ((str = reader.readLine()) != null) {
	    		System.out.println(str);
	    		result.add(str);
	    	}         
	    	reader.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	return result;
    }
}
