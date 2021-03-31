package Lesson22HW21;

import java.util.*;
import java.util.stream.Collectors;

public class HotelRelevance {
    static final HashSet<String> keyWords = new HashSet<>(Arrays.asList(
            "breakfast", "beach", "citycenter", "location", "metro", "view", "staff", "price"));

    public static int pointsFromReview(String review) {
        String[] revWords = review.replaceAll("[^A-Za-z ]+", "").toLowerCase(Locale.ROOT).split(" ");
        return (int) Arrays.stream(revWords)
                .filter(keyWords::contains).count();
    }

    public static String[] mostPopularHotel(String text) {
        String[] splitText = text.split("\n");
        HashMap<String, Integer> resultHM = new HashMap<>();
        String hotelId = "";
        for (String s : splitText) {
            if (s.matches("[0-9]+")) {
                hotelId = s;
                continue;
            } else if (!resultHM.containsKey(hotelId)) {
                resultHM.put(hotelId, pointsFromReview(s));
                continue;
            }
            resultHM.put(hotelId, resultHM.get(hotelId) + pointsFromReview(s));
        }
        return resultHM.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toArray(String[]::new);
    }

    /**
     * @return результат аналогичен pointsFromReview(), только дополнительно выводит в консоль каждую связку из Мапы
     * Map<String, Integer> где Key - ключевое слово, Value - количество слов в предложении.
     */

    public static int pointsFromReviewWithPrinting(String review) {
        String[] revWords = review.replaceAll("[^A-Za-z ]+", "").toLowerCase(Locale.ROOT).split(" ");
        return Arrays.stream(revWords)
                .filter(keyWords::contains)
                .collect(Collectors.toMap(s -> s, s -> 1, Integer::sum))
                .entrySet()
                .stream()
                .peek(System.out::println)
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
    }

    public static String[] mostPopularHotelWithExplain(String text) {
        String[] splitText = text.split("\n");
        HashMap<String, Integer> resultHM = new HashMap<>();
        String hotelId = "";
        for (String s : splitText) {
            if (s.matches("[0-9]+")) {
                hotelId = s;
                continue;
            } else if (!resultHM.containsKey(hotelId)) {
                System.out.println("Обзор на отель № " + hotelId);
                System.out.println(s);
                int points = pointsFromReviewWithPrinting(s);
                resultHM.put(hotelId, points);
                System.out.println("Количество очков за обзор : " + points);
                System.out.println("***************************");
                continue;
            }
            System.out.println("Обзор на отель № " + hotelId);
            System.out.println(s);
            int points = pointsFromReviewWithPrinting(s);
            resultHM.put(hotelId, resultHM.get(hotelId) + points);
            System.out.println("Количество очков за обзор : " + points);
            System.out.println("***************************");
        }
        System.out.println(resultHM);
        return resultHM.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toArray(String[]::new);
    }

    public static void main(String[] args) {
        String text = "1\n" +
                "This hotel has a nice view of the citycenter. The location is perfect.\n" +
                "2\n" +
                "The breakfast is ok. Regaring location, it is quite fat from the citycenter. But price is cheap, so it is ok.\n" +
                "1\n" +
                "Location is excellent, 5 min from citycenter. There is also a metro station pretty close to the hotel.\n" +
                "1\n" +
                "They said I couldn't take my dog. But there were other guests with dogs! That is not fair.\n" +
                "2\n" +
                "Very friendly staff and good cost-benefit ratio. Its location is a bit far from citycenter.\n" +
                "33\n" +
                "This hotel has a nice view of the citycenter. The location is perfect.\n" +
                "46\n" +
                "The breakfast is ok. Regaring location, it is quite fat from the citycenter. But price is cheap, so it is ok.\n" +
                "33\n" +
                "Location is excellent, 5 min from citycenter. There is also a metro station pretty close to the hotel.\n" +
                "Location is excellent, 5 min from citycenter. There is also a metro station pretty close to the hotel.\n" +
                "33\n" +
                "They said I couldn't take my dog. But there were other guests with dogs! That is not fair.\n" +
                "They said I couldn't take my dog. But there were other guests with dogs! That is not fair.\n" +
                "They said I couldn't take my dog. But there were other guests with dogs! That is not fair.\n" +
                "46\n" +
                "Very friendly staff and good cost-benefit ratio. Its location is a bit far from citycenter.";

        System.out.println(Arrays.toString(mostPopularHotel(text)));

        System.out.println(Arrays.toString(mostPopularHotelWithExplain(text)));
    }
}
