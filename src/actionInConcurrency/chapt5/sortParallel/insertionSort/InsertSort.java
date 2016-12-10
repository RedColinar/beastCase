package actionInConcurrency.chapt5.sortParallel.insertionSort;
//插入排序
public class InsertSort {
	public static void insertSort(int[] arr){
		int length = arr.length;
		int i,j,key;
		for(i = 1;i<length;i++){
			//key为要插入的元素，实际上就是遍历除了第0个元素的所有元素
			key = arr[i];
			j = i-1;
			//如果key比它前面的元素小，就交换它们的位置，直至所有已排序的部分按顺序排列。
			while(j >= 0 && key < arr[j]){
				arr[j+1] = arr[j];
				j--;
			}
			arr[j+1] = key;
		}
	}
	public static void main(String[] args) {
		int[] arr = new int[]{3,5,4,2,1};
		insertSort(arr);
		for (int i : arr) {
			System.out.print(i+" ");
		}
	}
}
