package org.rb.sbsec.model;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.annotations.ApiModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "파일 정리를 위한 파일 정보")
@Entity
@Table(name = "FILE_INFO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class FileInfo implements Serializable {
	private static final long serialVersionUID = 447216607501531919L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String fileName;
	
	private String fileExt;
	private Integer fileSize;
	private String directory;
	private String fileType;
	private String delYn;
	private String actionYn;
	private Timestamp updateDt;
	

}
