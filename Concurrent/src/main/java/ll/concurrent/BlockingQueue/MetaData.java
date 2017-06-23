package ll.concurrent.BlockingQueue;

public class MetaData {
	private String id;
	private String desc;

	public MetaData() {
		super();
	}

	public MetaData(String id) {
		super();
		this.id = id;
	}
	
	public MetaData(String id, String desc) {
		super();
		this.id = id;
		this.desc = desc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "MetaData [id=" + id + ", desc=" + desc + "]";
	}
	
	

}
