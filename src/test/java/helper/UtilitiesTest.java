package helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilitiesTest {

	@Test
	void pluralize_do() {
		assertEquals(" do", Utilities.pluralizeDoIfNeeded(true, false));
	}

	@Test
	void pluralize_do_and_negate() {
		assertEquals(" don't", Utilities.pluralizeDoIfNeeded(true, true));
	}

	@Test
	void dont_pluralize_do() {
		assertEquals(" does", Utilities.pluralizeDoIfNeeded(false, false));
	}

	@Test
	void dont_pluralize_do_and_negate() {
		assertEquals(" doesn't", Utilities.pluralizeDoIfNeeded(false, true));
	}

	@Test
	void pluralize_is() {
		assertEquals(" are", Utilities.pluralizeIsIfNeeded(true, false));
	}

	@Test
	void pluralize_is_and_negate() {
		assertEquals(" aren't", Utilities.pluralizeIsIfNeeded(true, true));
	}

	@Test
	void dont_pluralize_is() {
		assertEquals(" is", Utilities.pluralizeIsIfNeeded(false, false));
	}

	@Test
	void dont_pluralize_is_and_negate() {
		assertEquals(" isn't", Utilities.pluralizeIsIfNeeded(false, true));
	}
}
