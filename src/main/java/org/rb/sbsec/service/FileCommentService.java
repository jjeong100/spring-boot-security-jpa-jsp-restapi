/**
 * Spring Boot + JPA/Hibernate + PostgreSQL RESTful CRUD API Example (https://www.dariawan.com)
 * Copyright (C) 2020 Dariawan <hello@dariawan.com>
 *
 * Creative Commons Attribution-ShareAlike 4.0 International License
 *
 * Under this license, you are free to:
 * # Share - copy and redistribute the material in any medium or format
 * # Adapt - remix, transform, and build upon the material for any purpose,
 *   even commercially.
 *
 * The licensor cannot revoke these freedoms
 * as long as you follow the license terms.
 *
 * License terms:
 * # Attribution - You must give appropriate credit, provide a link to the
 *   license, and indicate if changes were made. You may do so in any
 *   reasonable manner, but not in any way that suggests the licensor
 *   endorses you or your use.
 * # ShareAlike - If you remix, transform, or build upon the material, you must
 *   distribute your contributions under the same license as the original.
 * # No additional restrictions - You may not apply legal terms or
 *   technological measures that legally restrict others from doing anything the
 *   license permits.
 *
 * Notices:
 * # You do not have to comply with the license for elements of the material in
 *   the public domain or where your use is permitted by an applicable exception
 *   or limitation.
 * # No warranties are given. The license may not give you all of
 *   the permissions necessary for your intended use. For example, other rights
 *   such as publicity, privacy, or moral rights may limit how you use
 *   the material.
 *
 * You may obtain a copy of the License at
 *   https://creativecommons.org/licenses/by-sa/4.0/
 *   https://creativecommons.org/licenses/by-sa/4.0/legalcode
 */
package org.rb.sbsec.service;

import java.util.ArrayList;
import java.util.List;

import org.rb.sbsec.exception.BadResourceException;
import org.rb.sbsec.exception.ResourceAlreadyExistsException;
import org.rb.sbsec.exception.ResourceNotFoundException;
import org.rb.sbsec.model.FileComment;
import org.rb.sbsec.repository.FileCommentRepository;
import org.rb.sbsec.specification.FileCommentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FileCommentService {
    
    @Autowired
    private FileCommentRepository fileCommentRepository;
    
    /**
     * 
     * @param id
     * @return
     */
    private boolean existsById(Long id) {
        return fileCommentRepository.existsById(id);
    }
    
    /**
     * 
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    public FileComment findById(Long id) throws ResourceNotFoundException {
    	FileComment fileComment = fileCommentRepository.findById(id).orElse(null);
        if (fileComment==null) {
            throw new ResourceNotFoundException("Cannot find Contact with id: " + id);
        }
        else return fileComment;
    }
    
    /**
     * 
     * @param pageNumber
     * @param rowPerPage
     * @return
     */
    public List<FileComment> findAll(int pageNumber, int rowPerPage) {
        List<FileComment> fileComment = new ArrayList<>();
        fileCommentRepository.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(fileComment::add);
        return fileComment;
    }
    
    public List<FileComment> findAllByFileName(Long fileId, int pageNumber, int rowPerPage) {
    	FileComment filter = new FileComment();
        filter.setFileId(fileId);
        Specification<FileComment> spec = new FileCommentSpecification(filter);
        
        List<FileComment> fileComment = new ArrayList<>();
        fileCommentRepository.findAll(spec, PageRequest.of(pageNumber - 1, rowPerPage)).forEach(fileComment::add);
        return fileComment;
    }
    
    /**
     * 
     * @param fileComment
     * @return
     * @throws BadResourceException
     * @throws ResourceAlreadyExistsException
     */
    public FileComment save(FileComment fileComment) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(fileComment.getFileId())) {
            if (fileComment.getId() != null && existsById(fileComment.getId())) { 
                throw new ResourceAlreadyExistsException("Contact with id: " + fileComment.getId() +
                        " already exists");
            }
            return fileCommentRepository.save(fileComment);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save contact");
            exc.addErrorMessage("FileComment is null or empty");
            throw exc;
        }
    }
    
    /**
     * 
     * @param fileComment
     * @throws BadResourceException
     * @throws ResourceNotFoundException
     */
    public void update(FileComment fileComment) 
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(fileComment.getFileId())) {
            if (!existsById(fileComment.getId())) {
                throw new ResourceNotFoundException("Cannot find fileComment with id: " + fileComment.getId());
            }
            fileCommentRepository.save(fileComment);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save contact");
            exc.addErrorMessage("Contact is null or empty");
            throw exc;
        }
    }
    
    
    /**
     * 
     * @param id
     * @throws ResourceNotFoundException
     */
    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) { 
            throw new ResourceNotFoundException("Cannot find contact with id: " + id);
        }
        else {
        	fileCommentRepository.deleteById(id);
        }
    }
    
    /**
     * 
     * @return
     */
    public Long count() {
        return fileCommentRepository.count();
    }
    
  
}
