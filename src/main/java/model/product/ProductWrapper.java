package model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.KataWrapper;

import java.util.Set;

@AllArgsConstructor
@Getter
public class ProductWrapper extends KataWrapper {

	private final Set<Client> clients;
}
