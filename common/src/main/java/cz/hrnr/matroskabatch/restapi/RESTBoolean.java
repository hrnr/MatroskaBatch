package cz.hrnr.matroskabatch.restapi;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.BooleanUtils;

@XmlRootElement
public class RESTBoolean {
	private boolean value;
	
	public RESTBoolean() {
		this.value = false;
	}
	
	public RESTBoolean(boolean value) {
		super();
		this.value = value;
	}

	public boolean isValue() {
		return value;
	}
	
	public boolean get() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return BooleanUtils.toStringTrueFalse(value);
	}
}
