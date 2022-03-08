package helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.file.Path;
import java.nio.file.Paths;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

	private static final String SRC_FOLDER_NAME = "src";
	private static final String MAIN_FOLDER_NAME = "main";
	private static final String TEST_FOLDER_NAME = "test";
	private static final String JAVA_FOLDER_NAME = "java";
	private static final String RESOURCES_FOLDER_NAME = "resources";

	public static final Path MAIN_RESOURCES_PATH = Paths.get(SRC_FOLDER_NAME, MAIN_FOLDER_NAME, RESOURCES_FOLDER_NAME);
	public static final Path TEST_RESOURCES_PATH = Paths.get(SRC_FOLDER_NAME, TEST_FOLDER_NAME, RESOURCES_FOLDER_NAME);

	public static final String FOREX_CSV = "/Forex.csv";
	public static final String PRICES_CSV = "/Prices.csv";
	public static final String PRODUCT_CSV = "/Product.csv";
}
