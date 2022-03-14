package helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Utilities {

	public static String pluralizeDoIfNeeded(boolean shouldPluralize, boolean negation) {
		StringBuilder sb = new StringBuilder();
		if (shouldPluralize) {
			sb.append(" do");
		} else {
			sb.append(" does");
		}
		if (negation) {
			sb.append("n't");
		}
		return sb.toString();
	}

	public static String pluralizeIsIfNeeded(boolean shouldPluralize, boolean negation) {
		StringBuilder sb = new StringBuilder();
		if (shouldPluralize) {
			sb.append(" are");
		} else {
			sb.append(" is");
		}
		if (negation) {
			sb.append("n't");
		}
		return sb.toString();
	}
}
