package org.rb.sbsec.ajax.sample.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Sample01 {
	//1.Collection으로부터 생성
	List<String> list = Arrays.asList("a","b","c");
	Stream<String> stream01 = list.stream();
	
	//2.Array로부터 생성
	String[] arr = {"a","b","c"};
	Stream<String> stream02 = Arrays.stream(arr);
	
	//3.Stream.of
	Stream<String> stream03 = Stream.of("a","b","c");
	
	//1.filter
	//조건에 맞는 요소만을 선택 
	Stream<String> stream04 = Stream.of("apple","banana","cherry","durian");
	Stream<String> filteredSteam = stream04.filter(str->str.length() > 5);
	
	//2.map
	//각요소의 대한 매핑
	Stream<String> stream05 = Stream.of("apple","banana","cherry","durian");
	Stream<Integer> mappedStream = stream05.map(str -> str.length());
	
	//3.flatMap
	//각요소애대해 매핑 함수적용 새로운 Stream
	Stream<List<Integer>> stream06 = Stream.of(Arrays.asList(1,2), Arrays.asList(3,4));
	Stream<Integer> flatMappedSteam = stream06.flatMap(list->list.stream());
			
	//4.sorted
	//요소를 정렬
	Stream<String> stream07 = Stream.of("banana","apple","cherry","durian");
	Stream<String> sortedSteam = stream07.sorted();
}
