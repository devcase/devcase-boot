package br.com.devcase.boot.sample.validation;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class Example {
	@NotNull
	private String fieldNotNull;
	@Max(value = 10)
	private Long maxTen;
	@NotNull(message = "{notNullCustom.message}")
	private String notNullWithCustomMessage;

	private Example(Builder builder) {
		this.fieldNotNull = builder.fieldNotNull;
		this.maxTen = builder.maxTen;
		this.notNullWithCustomMessage = builder.notNullWithCustomMessage;
	}

	public String getFieldNotNull() {
		return fieldNotNull;
	}

	public void setFieldNotNull(String fieldNotNull) {
		this.fieldNotNull = fieldNotNull;
	}

	public Long getMaxTen() {
		return maxTen;
	}

	public void setMaxTen(Long maxTen) {
		this.maxTen = maxTen;
	}

	public String getNotNullWithCustomMessage() {
		return notNullWithCustomMessage;
	}

	public void setNotNullWithCustomMessage(String notNullWithCustomMessage) {
		this.notNullWithCustomMessage = notNullWithCustomMessage;
	}

	/**
	 * Creates builder to build {@link Example}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Example}.
	 */
	public static final class Builder {
		private String fieldNotNull;
		private Long maxTen;
		private String notNullWithCustomMessage;

		private Builder() {
		}

		public Builder withFieldNotNull(String fieldNotNull) {
			this.fieldNotNull = fieldNotNull;
			return this;
		}

		public Builder withMaxTen(Long maxTen) {
			this.maxTen = maxTen;
			return this;
		}

		public Builder withNotNullWithCustomMessage(String notNullWithCustomMessage) {
			this.notNullWithCustomMessage = notNullWithCustomMessage;
			return this;
		}

		public Example build() {
			return new Example(this);
		}
	}

}
