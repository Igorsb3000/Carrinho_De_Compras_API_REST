package br.com.atlantic.api.config;

public abstract class ApiVersion {
	public static final String V1 = "/v1";

	private ApiVersion() {
		throw new IllegalStateException("Utility class");
	}
}
