package org.hillel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvFileDataProvider {

    private final Mapper mapToPerson;

    public CsvFileDataProvider(Mapper mapToPerson) {
        this.mapToPerson = mapToPerson;
    }

    public List<Person> provide(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream
                    .skip(1)
                    .filter(x -> !x.equals(""))
                    .map(mapToPerson::apply)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
