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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
import org.springframework.ui.Model;
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
    public ResponseEntity<List<FileInfo>> readFileList(@RequestParam("pageno") int pageno
    		                                          , @RequestParam("pagesize") int pagesize
    		                                          , @RequestParam("folder") String folder) throws Exception {
//    	fileInfoLogicService.getFileInfoList();
//    	 return ResponseEntity.ok().build();
//    	System.out.println("data : "+data);
//    	System.out.println("total : "+total);
    	System.out.println("pageno : "+pageno);
    	System.out.println("pagesize : "+pagesize);
    	System.out.println("folder : "+folder);
    	
//    	String folder = "D:\\torrent\\";
//    	return ResponseEntity.ok(fileInfoLogicService.getFileInfoList(folder));
//    	return ResponseEntity.ok(fileInfoLogicService.getFileInfoList());
//    	return ResponseEntity.ok(fileInfoLogicService.getFileInfoTypeList("F"));
    	return ResponseEntity.ok(fileInfoLogicService.getList(pageno,pagesize));
    	
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
}
