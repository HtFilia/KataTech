package model.product;

import model.KataWrapper;

import java.util.Map;

public record ProductWrapper(Map<Client, Map<Product, Integer>> clients) implements KataWrapper {
}
