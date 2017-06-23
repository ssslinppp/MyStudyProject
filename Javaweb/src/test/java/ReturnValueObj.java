import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("rawtypes")
@XmlRootElement
public class ReturnValueObj {
	@Override
	public String toString() {
		return "ReturnValueObj [value=" + value + ", exceptionMessage="
				+ exceptionMessage + ", success=" + success + ", returnValue="
				+ returnValue + "]";
	}

	private Map value;
	private ExceptionMessage exceptionMessage;
	private boolean success = true;
	private Object returnValue;

	public Object getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

	public ReturnValueObj() {
	}

	@SuppressWarnings("unchecked")
	public ReturnValueObj(String key, Object result) {
		value = new HashMap<String, List>();
		if (result == null)
			return;
		if (result instanceof List) {
			value.put(key, result);
		} else {
			List l = new ArrayList();
			l.add(result);
			value.put(key, l);
		}
	}

	public ReturnValueObj(ExceptionMessage msg) {
		this.success = false;
		this.exceptionMessage = msg;
	}

	public void joinReturnValue(String key, Object result) {
		if (result == null)
			return;
		if (value == null) {
			value = new HashMap<String, List>();
		}
		Object list = value.get(key);
		if (list == null) {
			list = new ArrayList();
			value.put(key, list);
		}
		if (!(list instanceof Collection))
			throw new IllegalArgumentException("value.get(" + key
					+ ") is not Collection");

		if (result instanceof Collection) {
			((Collection) list).addAll((Collection) result);
		} else {
			((Collection) list).add(result);
		}
	}

	public void addReturnValue(String key, Object result) {
		if (value == null) {
			value = new HashMap();
		}
		value.put(key, result);
	}

	public Map<String, List> getValue() {
		return value;
	}

	public void setValue(Map<String, List> value) {
		this.value = value;
	}

	public ExceptionMessage getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
