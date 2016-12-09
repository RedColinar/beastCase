package actionInConcurrency.chapt5.searchByFuture;

import java.util.concurrent.Callable;

public class SearchTask implements Callable<Integer>{
	int begin,end,searchValue;
	
	public SearchTask(int begin, int end, int searchValue) {
		super();
		this.begin = begin;
		this.end = end;
		this.searchValue = searchValue;
	}

	@Override
	public Integer call() throws Exception {
		return Main.search(searchValue, begin, end);
	}

}
