package org.rb.sbsec.logic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.rb.sbsec.ajax.sample.dto.FileInfoDto;
import org.rb.sbsec.model.FileComment;
import org.rb.sbsec.model.FileInfo;
import org.rb.sbsec.service.FileCommentService;
import org.rb.sbsec.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SampleLogicService {
	
	@Autowired
	public FileInfoService fileInfoService;
	
	@Autowired
	public FileCommentService fileCommentService;
	
	/**
	 * 파일 정보 조회
	 * @return
	 * @throws Exception
	 */
	public List<FileInfo> getFileInfoList(String _path) throws Exception {
//		List<FileInfo> list = fileInfoService.findAll(0, 100);
//		for(int index=0;index<list.size();index++) {
//			FileInfo fileInfo = list.get(index);
//			System.out.println(fileInfo.getFileName());
//		}
		
//		String _path = "D:\\sample\\"; 
        
//        ListFile( strDirPath ); 
		
		Path dirPath = Paths.get(_path);
		Stream<Path> walk = Files.walk(dirPath);
		 
		List<Path> list =  walk.filter(Files::isRegularFile)
//		                .filter(p -> p.getFileName().toString().equalsIgnoreCase(_name))
		                .collect(Collectors.toList());
		
		List<FileInfo> result = new ArrayList<FileInfo>();
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
		for(FileInfo Info:result) {
			System.out.println(Info.getFileName());
		}
		
		return result;
	}
	
	
	/**
	 * 파일 정보 조회
	 * @return
	 * @throws Exception
	 */
	public List<FileComment> getCommentList() throws Exception {
		List<FileComment> result = new ArrayList<FileComment>();
	
		return result;
	}
	
	/**
	 * 파일 정보 저장
	 * @return
	 */
	public List<FileInfo> insertfile(String _path) throws Exception {
		List<FileInfo> result = getFileInfoList(_path);
		for(int index=0;index<result.size();index++) {
			fileInfoService.save(result.get(index));
		}		
		return result;
	}
	
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
	
	public List<FileInfo> getFileList() throws Exception {
//		List<FileInfo> result = fileInfoService.findAll(0, 100);
		List<FileInfo> result = fileInfoService.findAll(1, 100);
//		for(int index=0;index<result.size();index++) {
//			fileInfoService.save(result.get(index));
//		}		
		return result;
		
		
	}
	
	public List<FileComment> getFileCommentList() {
		List<FileComment> result = new ArrayList<FileComment>();
		FileComment fileComment = new FileComment();
		fileComment.setFileId(Long.valueOf("1"));
		fileComment.setContent("테스트01");
		fileComment.setFileType("M");
		result.add(fileComment);
		
		fileComment.setFileId(Long.valueOf("1"));
		fileComment.setContent("테스트02");
		fileComment.setFileType("M");
		result.add(fileComment);
		
		fileComment.setFileId(Long.valueOf("1"));
		fileComment.setContent("테스트03");
		fileComment.setFileType("M");
		result.add(fileComment);
		
		fileComment.setFileId(Long.valueOf("2"));
		fileComment.setContent("테스트01");
		fileComment.setFileType("M");
		result.add(fileComment);
		
		fileComment.setFileId(Long.valueOf("2"));
		fileComment.setContent("테스트02");
		fileComment.setFileType("M");
		result.add(fileComment);
		
		fileComment.setFileId(Long.valueOf("2"));
		fileComment.setContent("테스트03");
		fileComment.setFileType("M");
		result.add(fileComment);
		
		
		
		return result;
	}
	/**
	 * 파일 코멘트
	 * @return
	 */
	public List<FileComment> insertfileComment() throws Exception {
		List<FileComment> result = getFileCommentList();
		for(int index=0;index<result.size();index++) {
			fileInfoService.save(result.get(index));
		}		
		return result;
	}
	
	public List<FileInfoDto> getFileInfo(){
		List<FileInfo> fileInfo = fileInfoService.findAll(1, 100);
		List<FileComment> fileComment = fileCommentService.findAll(1, 100);
		
//		return fileInfo.stream()
//				.flatMap(v1->fileComment.stream().map(v2->tuple(v1,v2)))
//				.filter(t->t.v1.getId().equals(t.v2.getFileId())) // join 조건
//				.map(t->t.v1) //t.v1 = fileInfo. t.v2=fileComment
//				.distaince()//중복 제거
//				.collect(Collectors.toList());
		
		Map<Long, FileInfo> fileIdMap = fileInfo.stream()
				.collect(Collectors.toMap(FileInfo::getId, Function.identity()));
		
		
		List<Pair<FileComment,FileInfo>> innerJoinList = fileComment.stream()
				.filter(it-> fileIdMap.containsKey(it.getId()))
				.map(it->Pair.of(it,fileIdMap.get(it.getId())))
				.collect(Collectors.toList());
		
		Set<Map.Entry<String, Integer>> entries = new HashSet<>();
		
		

		
		/** Inner Join **/
//		Map<Long, User> userIdMap = userList.stream()
//		        .collect(Collectors.toMap(User::getUserId, Function.identity()));
//
//		List<Pair<Order, User,>> innerJoinList = orderList.stream()
//		        .filter(it -> userIdMap.containsKey(it.getUserId()))
//		        .map(it -> Pair.of(it, userIdMap.get(it.getUserId())))
//		        .collect(Collectors.toList());
		
		/** Left Join **/
//		Map<Long, Order> orderIdMap = orderList.stream()
//		        .collect(Collectors.toMap(Order::getUserId, Function.identity()));
//
//		List<User> leftJoinUser = userList.stream()
//		        .filter(it -> !orderIdMap.containsKey(it.getUserId()))
//		        .collect(Collectors.toList());
		
		return null;
	}

}
