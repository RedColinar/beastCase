package actionInConcurrency.chapt5.sortParallel.oddEven;

public class OddEvenSort {
	//串行奇偶排序
	public static int[] oddEvenSort(int[] arr){
		int exchangeFlag = 1,start =  0;
		//exchangeFlag=1，说明上一次循环发生过数据交换，
		//那么这一次循环依然有可能有数据进行交换
		//若这里start=1，则上一次进行的是偶交换
		//跳出while循环的条件是，上一次循环没有数据交换，且为奇交换
		//
		while(exchangeFlag ==1 || start ==1){
			//exchangeFlag=0表示在while中目前没有数据发生变动
			exchangeFlag = 0;
			for(int i = start;i < arr.length-1;i+=2){
				if(arr[i] > arr[i+1]){
					int temp = arr[i];
					arr[i] = arr[i+1];
					arr[i+1] = temp;
					//发生过数据交换后，令exchangeFlag = 1
					exchangeFlag = 1;
					//一旦exchangeFlag = 1，则至少要过两次循环
				}
			}
			//第0位和第1位，第2和第4，进行比较，是偶交换
			//第一次for循环后，令start = 1，
			//第1和第2，第3和第4比较，是奇交换
			if(start == 0)
				start = 1;
			else
				start = 0;
		}
		return arr;
	}

	public static void main(String[] args) {
		//至少写5个数
		for (int i : oddEvenSort(new int[]{3,5,4,2,1})) {
			System.out.print(i+" ");
		}
 	}
}
