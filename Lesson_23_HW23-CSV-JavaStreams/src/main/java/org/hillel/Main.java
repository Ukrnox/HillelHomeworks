package org.hillel;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Mapper mapper = new DataMapper();
        CsvFileDataProvider dataProvider = new CsvFileDataProvider(mapper);
        //я там поменял в горе Michigantown немного данные(добавил количество людей в городе и изменил их возраст), для теста.
        List<Person> provide = dataProvider.provide(
                "src\\main\\resources\\100Records.csv");

        System.out.println(provide.size());
        PersonsStat personsStat = new PersonsStat(provide);
        System.out.println("**********-getAverageSalary-***********");
        System.out.println(personsStat.getAverageSalary());
        System.out.println("**********-medianSalary-***************");
        System.out.println(personsStat.medianSalary());
        System.out.println("**********-ninetyFifeSalary-***********");
        System.out.println(personsStat.ninetyFifeSalary());
        System.out.println("**********-workersInCities-************");
        Map<String, Integer> map = personsStat.workersInCities();
        int a1 = 0;
        for (Map.Entry<String, Integer> a : map.entrySet()) {
            System.out.println(a.getKey() + " : " + a.getValue());
            a1++;
            if (a1 == 100) {
                break;
            }
        }
        System.out.println("**********-personsAgeCountInCities-***********");
        int a2 = 0;
        for (String a :
                personsStat.personsAgeCountInCitiesList()) {
            System.out.println(a);
            a2++;
            if (a2 == 100) {
                break;
            }
        }
        System.out.println("*****-personsAgeGroupGenderCountInCities-*****");
        int a3 = 0;
        List<String> strings = personsStat.personsAgeGroupGenderCountInCitiesList();
        for (String a :
                strings) {
            System.out.println(a);
            a3++;
            if (a3 == 100) {
                break;
            }
        }
        System.out.println("***********************The End of tests***********");
    }
}
