package utils;

import java.util.Random;

public class GeradorDeCpf {

	public static String retornarCpf() {

		String CPF = generateRandomChars("0123456789", 11);

		return CPF;
	}

	public static String generateRandomChars(String candidateChars, int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
		}

		return sb.toString();
	}
}
