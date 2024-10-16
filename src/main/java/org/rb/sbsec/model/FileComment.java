package org.rb.sbsec.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.annotations.ApiModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "파일 코멘트")
@Entity
@Table(name = "FILE_COMMENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class FileComment implements Serializable {
	
	private static final long serialVersionUID = -3924077061657770175L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private Long fileId;
	
	private String content;
	private String fileType;
	private Timestamp createDt;
	
//	@OneToMany(mappedBy = "fileComment")
//	private Set<FileInfo> fileInfo = new HashSet<>();
	
//	public void add(FileInfo fileInfo) {
//		fileInfo.setFileComment(this);
//		getFileInfo().add(fileInfo);
//	}
	

}
