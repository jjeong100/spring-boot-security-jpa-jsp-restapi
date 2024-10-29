package org.rb.sbsec.logic;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.rb.sbsec.model.FileInfo;
import org.rb.sbsec.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileInfoLogicService {
	
	@Autowired
	public FileInfoService fileInfoService;
	
	/**
	 * 파일 정보 조회
	 * @return
	 * @throws Exception
	 */
	public List<FileInfo> getFileInfoList(String folder) throws Exception {
//		List<FileInfo> list = fileInfoService.findAll(0, 100);
//		for(int index=0;index<list.size();index++) {
//			FileInfo fileInfo = list.get(index);
//			System.out.println(fileInfo.getFileName());
//		}
		List<FileInfo> result = new ArrayList<FileInfo>();
		
		try {
			String _path = folder;//"D:\\sample\\"; 
	        
	//        ListFile( strDirPath ); 
			
			Path dirPath = Paths.get(_path);
			Stream<Path> walk = Files.walk(dirPath);
			 
			List<Path> list =  walk.filter(Files::isRegularFile)
	//		                .filter(p -> p.getFileName().toString().equalsIgnoreCase(_name))
			                .collect(Collectors.toList());
			
			for(int index=0;index<list.size();index++) {
				Path path = list.get(index);
				FileInfo info = new FileInfo();
				info.setFileName(path.getFileName().toString());
				info.setDirectory(path.getParent().toString());
				info.setFileExt(info.getFileName().substring(info.getFileName().lastIndexOf(".") + 1));
				info.setFileSize((Long)Files.size(path));
				info.setFileType("M");
				info.setDelYn("N");
				info.setActionYn("N");
				info.setFilePath(path.getParent()+"\\"+path.getFileName());
//				if("jpg".equals(info.getFileExt().toLowerCase())) {
//					info.setTagImg("<img src='"+info.getDirectory()+File.separator+info.getFileName()+"'/>");
//				}else {
//					info.setTagImg("");
//				}
				result.add(info);
			}
//			for(FileInfo Info:result) {
//				System.out.println(Info.getFileName());
//			}
		}catch(java.nio.file.AccessDeniedException e) {
			
		}
		
		return result;
	}
	
	/**
	 * 파일 정보 조회
	 * @return
	 * @throws Exception
	 */
	public List<String> getFileInfoPathList(String folder) throws Exception {
		List<String> result  = new ArrayList<String>();
		
		try {
			String _path = folder;//"D:\\sample\\"; 
	        
	//        ListFile( strDirPath ); 
			
			Path dirPath = Paths.get(_path);
			Stream<Path> walk = Files.walk(dirPath);
			 
			List<Path> list =  walk.filter(Files::isRegularFile)
	//		                .filter(p -> p.getFileName().toString().equalsIgnoreCase(_name))
			                .collect(Collectors.toList());
			
			for(int index=0;index<list.size();index++) {
				Path path = list.get(index);
				result.add(path.toString());
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<FileInfo> getList(int pageno, int pagesize) throws Exception {
		return fileInfoService.findAll(pageno,pagesize);
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<FileInfo> getFileInfoList() throws Exception {
		return fileInfoService.findAll();
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<FileInfo> getFileInfoTypeList(String type) throws Exception {
		return fileInfoService.findByType(type);
	}
	
	/**
	 * 파일 정보 저장
	 * @return
	 */
	public List<FileInfo> insertfile(String folder)  throws Exception {
		List<FileInfo> result = getFileInfoList(folder);
		for(int index=0;index<result.size();index++) {
			fileInfoService.save(result.get(index));
		}		
		return result;
	}
	
	/**
	 * 파일 저장 
	 */
	public List<FileInfo> insertFileBatch(String folder)  throws Exception {
		List<FileInfo> result = getFileInfoList(folder);
			fileInfoService.batchInsert(result);
		return result;
	}
	
	public void moveFileTo(String filePath) {
		try {
			String pathString = "D://moveDir//";
			Path directoryPath = Paths.get(pathString);
			// 디렉토리 생성            
			Files.createDirectories(directoryPath);
			
			//실제 하드 디스크의 파일 조회
//			List<FileInfo> fileList = getFileInfoList(folder); 
			
			
			String fileName = new File(filePath).getName();
			Path oldFile = Paths.get("");
			Path newFile = Paths.get(pathString+fileName);
			
			Files.move(oldFile, newFile, StandardCopyOption.ATOMIC_MOVE);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void deleteFileTo(String filePath) {
		try {
			File deleteFile = new File(filePath);
			
			//파일이 존재하는지 체크 존재할경우 true, 존재하지않을경우 false
			if(deleteFile.exists()) {
				deleteFile.delete();
			}else {
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
//	/**
//	 * 파일 정보 저장
//	 * @return
//	 */
//	public List<FileComment> insertComment()  throws Exception {
//		List<FileComment> result = getFileInfoList();
//		for(int index=0;index<result.size();index++) {
//			fileInfoService.save(result.get(index));
//		}		
//		return result;
//	}
	
//	public test<T,E,G> get01()
	
	 // 재귀함수 
//    private static void ListFile( String strDirPath ) { 
//         
//        File path = new File( strDirPath ); 
//        File[] fList = path.listFiles(); 
//         
//        for( int i = 0; i < fList.length; i++ ) { 
//             
//            if( fList[i].isFile() ) { 
//                System.out.println( fList[i].getPath() );  // 파일의 FullPath 출력 
//            } 
//            else if( fList[i].isDirectory() ) { 
//                ListFile( fList[i].getPath() );  // 재귀함수 호출 
//            } 
//        } 
//    } 

}

//class test<T,E,G> {
//	T test01;
//	E test02;
//	G test03;
//}
