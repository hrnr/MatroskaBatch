package cz.hrnr.matroskabatch.restapi;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Wraps boolean value for JAXB
 */
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
	
	/**
	 * Gets stored value. Same as {@link #get()}
	 * @return wrapped value
	 */
	public boolean isValue() {
		return value;
	}
	
	/**
	 * Gets stored value. Same as {@link #isValue()}
	 * @return wrapped value
	 */
	public boolean get() {
		return value;
	}
	
	/**
	 * Sets wrapped value.
	 * @param value
	 */
	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}
}
