package actionInConcurrency.chapt5.sortParallel.insertionShellSort;

public class ShellSortParallel {
	//串行的希尔排序实现
	public static void shellSort(int[] arr){
		//计算出最大的h值
		int h = 1;
		int len = arr.length;
		while(h <= len/3){
			h = h*3 +1;
		}
		//正式进入希尔排序
		while(h > 0){
			for(int i = h;i<arr.length;i++){
				if(arr[i]<arr[i-h]){
					int tmp = arr[i];
					int j = i - h;
					while(j >= 0 && arr[i] > tmp){
						arr[j+h] = arr[j];
						j -= h;
					}
					arr[j + h] = tmp;
				}
			}
			//计算出下一个h值
			h = (h - 1)/3;
		}
	}
	public static void main(String[] args) {
		int[] arr = new int[]{3,5,4,2,1};
		shellSort(arr);
		for (int i : arr) {
			System.out.print(i+" ");
		}
	}
}
