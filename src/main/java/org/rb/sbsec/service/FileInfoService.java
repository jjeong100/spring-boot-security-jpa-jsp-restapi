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
import org.rb.sbsec.model.FileInfo;
import org.rb.sbsec.repository.FileInfoRepository;
import org.rb.sbsec.specification.FileInfoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FileInfoService {
    
    @Autowired
    private FileInfoRepository fileInfoRepository;
    
    /**
     * 
     * @param id
     * @return
     */
    private boolean existsById(Long id) {
        return fileInfoRepository.existsById(id);
    }
    
    /**
     * 
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    public FileInfo findById(Long id) throws ResourceNotFoundException {
        FileInfo fileInfo = fileInfoRepository.findById(id).orElse(null);
        if (fileInfo==null) {
            throw new ResourceNotFoundException("Cannot find Contact with id: " + id);
        }
        else return fileInfo;
    }
    
    /**
     * 
     * @param pageNumber
     * @param rowPerPage
     * @return
     */
    public List<FileInfo> findAll(int pageNumber, int rowPerPage) {
        List<FileInfo> fileInfo = new ArrayList<>();
        fileInfoRepository.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(fileInfo::add);
        return fileInfo;
    }
    
    public List<FileInfo> findAllByFileName(String name, int pageNumber, int rowPerPage) {
        FileInfo filter = new FileInfo();
        filter.setFileName(name);
        Specification<FileInfo> spec = new FileInfoSpecification(filter);
        
        List<FileInfo> fileInfo = new ArrayList<>();
        fileInfoRepository.findAll(spec, PageRequest.of(pageNumber - 1, rowPerPage)).forEach(fileInfo::add);
        return fileInfo;
    }
    
    /**
     * 
     * @param fileInfo
     * @return
     * @throws BadResourceException
     * @throws ResourceAlreadyExistsException
     */
    public FileInfo save(FileInfo fileInfo) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(fileInfo.getFileName())) {
            if (fileInfo.getId() != null && existsById(fileInfo.getId())) { 
                throw new ResourceAlreadyExistsException("Contact with id: " + fileInfo.getId() +
                        " already exists");
            }
            return fileInfoRepository.save(fileInfo);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save contact");
            exc.addErrorMessage("FileInfo is null or empty");
            throw exc;
        }
    }
    
    /**
     * 
     * @param fileInfo
     * @throws BadResourceException
     * @throws ResourceNotFoundException
     */
    public void update(FileInfo fileInfo) 
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(fileInfo.getFileName())) {
            if (!existsById(fileInfo.getId())) {
                throw new ResourceNotFoundException("Cannot find FileInfo with id: " + fileInfo.getId());
            }
            fileInfoRepository.save(fileInfo);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save contact");
            exc.addErrorMessage("Contact is null or empty");
            throw exc;
        }
    }
    
//    public void updateAddress(Long id, Address address) 
//            throws ResourceNotFoundException {
//        Contact contact = findById(id);
//        contact.setAddress1(address.getAddress1());
//        contact.setAddress2(address.getAddress2());
//        contact.setAddress3(address.getAddress3());
//        contact.setPostalCode(address.getPostalCode());
//        fileInfoRepository.save(contact);        
//    }
    
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
        	fileInfoRepository.deleteById(id);
        }
    }
    
    /**
     * 
     * @return
     */
    public Long count() {
        return fileInfoRepository.count();
    }
}
