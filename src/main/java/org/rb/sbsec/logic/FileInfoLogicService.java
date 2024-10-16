package org.rb.sbsec.logic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.rb.sbsec.model.FileComment;
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
