package actionInConcurrency.chapt5.productionAndConsumptionPattern;
//商品
public final class Commodity {
	private final int id;
	public int getId() {
		return id;
	}
	
	public Commodity(int id) {
		super();
		this.id = id;
	}
	public Commodity(String id) {
		super();
		this.id = Integer.valueOf(id);
	}
	
	@Override
	public String toString() {
		return "Commodity [id=" + id + "]";
	}
	
}
