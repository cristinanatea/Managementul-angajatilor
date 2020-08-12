package com.natea.pm.backend;

public class Commons {
	public final static String DEFAULT_PASSWORD = "pass";

	public enum RequestStatus {
		pending(0), rejected(1), approved(2);

		private final int value;

		private RequestStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	};
}
