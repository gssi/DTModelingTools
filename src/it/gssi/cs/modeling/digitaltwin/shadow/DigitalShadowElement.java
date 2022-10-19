package it.gssi.cs.modeling.digitaltwin.shadow;

public class DigitalShadowElement {

	private String parameter;
	private float value;
	
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public DigitalShadowElement(String parameter, float value) {
		super();
		this.parameter = parameter;
		this.value = value;
	}
	
}
