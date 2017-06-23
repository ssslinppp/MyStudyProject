import java.util.List;

public class ExceptionMessage extends RuntimeException {
	private static final long serialVersionUID = -4930427764750768976L;

	private Integer errorCode;

	private List list;

	private String errorMessage;

	/**
	 * 定义新字段 用于承载后台传递的errorMsg;
	 * 最初前后台传递错误信息是通过约定errorCode来进行的，考虑到这样做比较繁琐，
	 * 所以添加了message字段，在后台直接将错误信息赋值给message，前台直接显示；
	 */
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public ExceptionMessage() {

	}

	public ExceptionMessage(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public ExceptionMessage(Integer errorCode, List list) {
		this.errorCode = errorCode;
		this.list = list;
	}

	@Deprecated
	public ExceptionMessage(String msg) {
		super(msg);
		setErrorMessage(msg);
	}
	
	/**
	 * 
	 * @param msg
	 * @param isByMessageField
	 */
	public ExceptionMessage(String msg, boolean isByMessageField) {
		super(msg);
		if (isByMessageField) {
			setMessage(msg);
		} else {
			setErrorMessage(msg);
		}
	}

	public ExceptionMessage(Integer errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public ExceptionMessage(Integer errorCode, String errorMessage, String alertMsg) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.message = alertMsg;
	}

	public ExceptionMessage(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ExceptionMessage(Throwable cause) {
		super(null, cause);
		if(cause instanceof ExceptionMessage) {
			this.setMessage(((ExceptionMessage)cause).message);
		}
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public static void main(String[] args) {
//		new ExceptionMessage(3);
	}
	
	/**
	 * 用alertMsg生成ExcetionMessage对象
	 * @param msg
	 * @return
	 */
	public static ExceptionMessage newInstance(String msg) {
		ExceptionMessage em = new ExceptionMessage();
		em.setMessage(msg);
		return em;
	}
}
