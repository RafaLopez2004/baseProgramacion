package proyecto_final;

public class ColumnOrder {
	private String index;
	private String order;
	public ColumnOrder(String index, String order) {
		this.index = index;
		this.order = order;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
}