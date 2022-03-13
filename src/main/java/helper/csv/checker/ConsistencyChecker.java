package helper.csv.checker;

import exception.csv.consistency.CurrencyNotLinkedToEurException;
import exception.csv.consistency.ProductNotDefinedException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.forex.Currency;
import model.forex.ForexWrapper;
import model.price.PriceWrapper;
import model.product.Product;
import model.product.ProductWrapper;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConsistencyChecker {

	public static void checkConsistency(ForexWrapper forexWrapper, ProductWrapper productWrapper, PriceWrapper priceWrapper) {
		checkCurrencyPairs(forexWrapper);
		checkCurrenciesCovered(forexWrapper, priceWrapper);
		checkProducts(productWrapper, priceWrapper);
	}

	static void checkCurrencyPairs(ForexWrapper forexWrapper) {
		Set<Currency> currencies = forexWrapper.currencies();
		for (Currency currency : currencies) {
			if (!currency.equals(Currency.EUR) &&
					!forexWrapper.conversions().containsKey(Pair.of(currency, Currency.EUR))
					&& !forexWrapper.conversions().containsKey(Pair.of(Currency.EUR, currency))) {
				throw new CurrencyNotLinkedToEurException(currency);
			}
		}
	}

	static void checkCurrenciesCovered(ForexWrapper forexWrapper, PriceWrapper priceWrapper) {
		Set<Currency> availableCurrencies = forexWrapper.currencies();
		Set<Currency> currencies = priceWrapper.currencies();
		currencies.removeAll(availableCurrencies);
		if (!currencies.isEmpty()) {
			throw new CurrencyNotLinkedToEurException(currencies);
		}
	}

	static void checkProducts(ProductWrapper productWrapper, PriceWrapper priceWrapper) {
		Set<Product> availableProducts = priceWrapper.products();
		Set<Product> products = productWrapper.products();
		products.removeAll(availableProducts);
		if (!products.isEmpty()) {
			throw new ProductNotDefinedException(products);
		}
	}
}
