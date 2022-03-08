package helper.csv.converter;

import model.KataWrapper;

import java.util.List;

@FunctionalInterface
public interface IKataConverter {

	KataWrapper convert(List<String[]> lines);
}
