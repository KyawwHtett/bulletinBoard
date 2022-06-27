package cgm.ojt.bulletin.common;

import java.security.SecureRandom;

public final class TokenGenerator {

	/**
	 * chars.
	 */
	private final String chars = "ABCDEFGJKLMNPRSTUVWXYZ";

	/**
	 * numbers.
	 */
	private final String numbers = "0123456789";

	/**
	 * SecureRandom from java security.
	 */
	private final SecureRandom random = new SecureRandom();

	/**
	 * char[] random to return token
	 */
	private final char[] rdm;

	/**
	 * Instantiates a new token generator.
	 *
	 * @param int length token length
	 */
	public TokenGenerator(int length) {
		if (length < 1)
			throw new IllegalArgumentException("length < 1: " + length);
		rdm = new char[length];
	}

	/**
	 * Generate new token
	 */
	public String getToken() {
		String symbols = chars + numbers + chars.toLowerCase();
		for (int idx = 0; idx < rdm.length; ++idx) {
			rdm[idx] = symbols.charAt(random.nextInt(symbols.length()));
		}
		return new String(rdm);
	}

}