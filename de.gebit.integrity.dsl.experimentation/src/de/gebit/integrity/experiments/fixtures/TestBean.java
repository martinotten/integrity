package de.gebit.integrity.experiments.fixtures;

import java.math.BigDecimal;
import java.util.List;

public class TestBean extends SuperTestBean {

	private int primitiveInt;

	private String localPrefixedString;

	private String nonaccessibleString;

	private InnerBean innerBeanParam;

	private InnerBean[] innerBeanArrayParam;

	private List<InnerBean> innerBeanListParam;

	public String getPrefixedString() {
		return localPrefixedString;
	}

	public void setPrefixedString(String localPrefixedString) {
		this.localPrefixedString = localPrefixedString;
	}

	public String getNonaccessibleString() {
		return nonaccessibleString;
	}

	public InnerBean[] getInnerBeanArrayParam() {
		return innerBeanArrayParam;
	}

	public void setInnerBeanArrayParam(InnerBean[] innerBeanArrayParam) {
		this.innerBeanArrayParam = innerBeanArrayParam;
	}

	public List<InnerBean> getInnerBeanListParam() {
		return innerBeanListParam;
	}

	public void setInnerBeanListParam(List<InnerBean> innerBeanListParam) {
		this.innerBeanListParam = innerBeanListParam;
	}

	public int getPrimitiveInt() {
		return primitiveInt;
	}

	public void setPrimitiveInt(int primitiveInt) {
		this.primitiveInt = primitiveInt;
	}

	public InnerBean getInnerBeanParam() {
		return innerBeanParam;
	}

	public void setInnerBeanParam(InnerBean innerBeanParam) {
		this.innerBeanParam = innerBeanParam;
	}

	public static class InnerBean {

		/**
		 * Another comment.
		 */
		private BigDecimal innerParameter;

		public BigDecimal getInnerParameter() {
			return innerParameter;
		}

		public void setInnerParameter(BigDecimal innerParameter) {
			this.innerParameter = innerParameter;
		}

	}

}
