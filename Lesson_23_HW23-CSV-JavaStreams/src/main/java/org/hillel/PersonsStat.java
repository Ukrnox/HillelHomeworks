package org.hillel;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersonsStat {
    private final List<Person> data;

    public PersonsStat(List<Person> data) {
        this.data = data.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<Person> getData() {
        return new ArrayList<>(data);
    }

    private double averageNum(Stream<Double> stream) {
        return stream.reduce(0.0, Double::sum) / data.size();
    }

    public double getAverageSalary() {
        return averageNum(data.stream()
                .map(Person::getSalary));
    }

    public List<String> workersInCitiesList() {
        List<String> resultList = new LinkedList<>();
        Map<String, Integer> stringIntegerMap = workersInCities();
        for (Map.Entry<String, Integer> a : stringIntegerMap.entrySet()) {
            resultList.add(a.getKey() + " : " + a.getValue());
        }
        return resultList;
    }

    public Map<String, Integer> workersInCities() {
        return data.stream()
                .map(Person::getCity)
                .collect(Collectors.toMap(s -> s, s -> 1, Integer::sum));
    }

    public double medianSalary() {
        return data.stream()
                .map(Person::getSalary)
                .sorted()
                .skip((data.size() / 2) - 1)
                .limit(2)
                .reduce((a, b) -> (a + b) / 2)
                .orElse(0.0);
    }

    public double ninetyFifeSalary() {
        return data.stream()
                .map(Person::getSalary)
                .sorted()
                .skip(data.size() / 100 * 95)
                .findFirst()
                .orElse(0.0);
    }

    public double ninetyNineSalary() {
        return data.stream()
                .map(Person::getSalary)
                .sorted()
                .skip(data.size() / 100 * 99)
                .findFirst()
                .orElse(0.0);
    }

    public Map<String, Map<Double, Long>> personsAgeCountInCities() {
        return data.stream()
                .collect(Collectors.groupingBy(Person::getCity,
                        Collectors.groupingBy(Person::getAgeInYrs, Collectors.counting())));

    }

    public List<String> personsAgeCountInCitiesList() {
        List<String> resultList = new LinkedList<>();
        Map<String, Map<Double, Long>> stringMapMap = personsAgeCountInCities();
        for (Map.Entry<String, Map<Double, Long>> a :
                stringMapMap.entrySet()) {
            for (Map.Entry<Double, Long> b :
                    a.getValue().entrySet()) {
                resultList.add(a.getKey() + " : " + b.getKey() + " : " + b.getValue());
            }
        }
        return resultList;
    }

    public List<String> personsAgeGroupGenderCountInCitiesList() {
        List<String> resultList = new LinkedList<>();
        Map<String, Map<String, Map<String, Long>>> stringMapMap = personsAgeGroupGenderCountInCities();
        for (Map.Entry<String, Map<String, Map<String, Long>>> a :
                stringMapMap.entrySet()) {
            for (Map.Entry<String, Map<String, Long>> b :
                    a.getValue().entrySet()) {
                for (Map.Entry<String, Long> c :
                        b.getValue().entrySet()) {
                    resultList.add(a.getKey() + " : " + b.getKey() + " : " + c.getKey() + " : " + c.getValue());
                }
            }
        }
        return resultList;
    }


    public Map<String, Map<String, Map<String, Long>>> personsAgeGroupGenderCountInCities() {
        return data.stream()
                .collect(Collectors.groupingBy(Person::getCity, Collectors.groupingBy(Person::getGender,
                        Collectors.groupingBy((x) -> {
                            double age = x.getAgeInYrs();
                            if (age >= 0 && age < 20) {
                                return " 0-19 ";
                            } else if (age >= 20 && age < 40.99) {
                                return " 20-39 ";
                            } else if (age >= 40 && age < 59.99) {
                                return " 40-59 ";
                            } else if (age >= 60 && age < 79.99) {
                                return " 60-79 ";
                            } else if (age >= 80 && age < 100) {
                                return " 80-100 ";
                            }
                            return "No Age Group!";
                        }, Collectors.counting()))));

    }
}
