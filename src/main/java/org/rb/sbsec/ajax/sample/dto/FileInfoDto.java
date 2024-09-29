package org.rb.sbsec.ajax.sample.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class FileInfoDto {

	private Long id;
	private String fileName;
	
	private String fileExt;
	private Long fileSize;
	private String directory;
	private String fileType;
	private String delYn;
	private String actionYn;
	private Timestamp updateDt;
	
	private String content; 
	
}
