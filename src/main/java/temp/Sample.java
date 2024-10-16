package temp;

import java.util.Arrays;

public class Sample {
	
	public static void main(String[] args) {
		Sample sample = new Sample();
		sample.process();
	}
	
	/**
	**/		
	public void process() {
		System.out.println("---------------- Sample St------------------");
//		
//		list.stream()
//		  .filter(el -> {
//		    System.out.println("filter() was called.");
//		    return el.contains("a");
//		  })
//		  .map(el -> {
//		    System.out.println("map() was called.");
//		    return el.toUpperCase();
//		  })
//		  .findFirst();
		int[] arr  = {1, 2, 3, 4, 5, 6, 7};
		int sum = Arrays.stream(arr)
				.filter(n -> n%2==1).sum();
		
		System.out.println(sum);
		
		System.out.println("---------------- Sample Ed------------------");
	}

}
