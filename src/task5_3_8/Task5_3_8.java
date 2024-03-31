package task5_3_8;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Task5_3_8 {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Please choose a film to watch:");

            RandomMoviePicker5 moviePicker = new RandomMoviePicker5();
            ITunesProduct[] movies = moviePicker.getRandomMovieNames();
            for (int i = 0; i < movies.length; i++) {
                int number = i + 1;
                System.out.println(number + ": " + movies[i].name + " (" + movies[i].year + ")");
            }

            System.out.println("Continue the selection? Answer 'yes' or 'no'.");
        } while (scanner.nextLine().equalsIgnoreCase("yes"));

        scanner.close();
        System.out.println("Coincidences are not found. Closing the program.");
    }
}

class RandomMoviePicker5 {
    PageDownloader downloader = new PageDownloader();

    ITunesProduct[] getRandomMovieNames() {
        String url = "https://randommer.io/random-movies";
        String page = downloader.downloadWebPage(url);

        ITunesProduct[] movies = new ITunesProduct[16];
        int endIndex = 0;
        int count = 0;
        for (int i = 0; i < 16; i++) {
            int captionIndex = page.indexOf("<div class=\"caption\"", endIndex);
            int startIndex = captionIndex + 52;
            endIndex = page.indexOf("</div>", startIndex) - 28;
            String movieName = page.substring(startIndex, endIndex);
            String nameWithoutYear = movieName.substring(0, movieName.length() - 6);
            String year = movieName.substring(movieName.length() - 5, movieName.length() - 1);
            ITunesProduct newMovie = new ITunesProduct(nameWithoutYear, year);

            if (!hasDuplicate(movies, newMovie)) {
                movies[count] = newMovie;
                count++;
            }
        }
        return Arrays.copyOf(movies, count);
    }

    private boolean hasDuplicate(ITunesProduct[] movies, ITunesProduct newMovie) {
        for (ITunesProduct movie : movies) {
            if (movie != null && movie.equals(newMovie)) {
                return true;
            }
        }
        return false;
    }
}

class PageDownloader {
    public String downloadWebPage(String url) {
        StringBuilder result = new StringBuilder();
        String line;
        URLConnection urlConnection;
        try {
            urlConnection = new URL(url).openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (InputStream is = urlConnection.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result.toString();
    }
}

class ITunesProduct {
    String name;
    String year;
    String artistName;
    String collectionName;
    String previewUrl;
    String country;

    ITunesProduct(String inputName, String inputYear) {
        this.name = inputName;
        this.year = inputYear;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ITunesProduct that = (ITunesProduct) obj;
        return name.equals(that.name) && year.equals(that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year);
    }
}





//// ПРИМЕР 4  _все работает, взято из задания task5_3_7
//import java.awt.*;
//import java.io.*;
//import java.net.URL;
//import java.net.URLConnection;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Scanner;
//
//public class Task5_3_8 {
//    public static void main(String[] args) throws IOException {
//        System.out.println("""
//                Задание:\s
//                Модуль 5. Интерфейсы, абстрактные классы, статические методы. Задание №3:\s
//                    8. Доработайте медиабраузер iTunes: он будет сохранять все найденные результаты в массив,
//                       далее давай пользователю возможность поискать еще раз; но скрывая повторяющиеся
//                       результаты (сверяясь с массивом)\s
//
//                Решение:\s""");
//
//        RandomMoviePicker5 moviePicker = new RandomMoviePicker5();
//        ITunesProduct[] movies = moviePicker.getRandomMovieNames();
//        System.out.println("Choose film to watch: ");
////        System.out.println(Arrays.toString(movies));
////        System.out.println(/*Arrays.toString(movies)*/movies);
//        for (int i=0; i<movies.length; i++) {
//            int number = i+1;
//            System.out.println(number + ": " + movies[i].name + "(" + movies[i].year + ")");
//        }
//        System.out.print("Enter chosen film number: ");
//        Scanner scanner = new Scanner(System.in);
//        int chosenNumber = scanner.nextInt();
//        int chosenIndex = chosenNumber - 1;
//        ITunesMoviePlayer player = new ITunesMoviePlayer();
//        player.playMovie(movies[chosenIndex].name);
//    }
//}
//
//class RandomMoviePicker5 {
//    PageDownloader downloader = new PageDownloader();
//
//    ITunesProduct[] getRandomMovieNames() {
//        String url = "https://randommer.io/random-movies";
//        String page = downloader.downloadWebPage(url);
//
//        ITunesProduct[] movies = new ITunesProduct[16];
//        int endIndex = 0;
//        for (int i=0; i<16; i++) {
//            int captionIndex = page.indexOf("<div class=\"caption\"", endIndex);
//            int startIndex = captionIndex + 52;
//            endIndex = page.indexOf("</div>", startIndex) - 28;
//            String movieName = page.substring(startIndex, endIndex);
//            String nameWithoutYear = movieName.substring(0, movieName.length() - 6);
//            String year = movieName.substring(movieName.length()-5, movieName.length()-1);
//            movies[i] = new ITunesProduct(nameWithoutYear, year);
//        }
//        return movies;
//    }
//}
//
//class ITunesProduct {
//    String name;
//    String year;
//    String artistName;
//    String collectionName;
//    String previewUrl;
//    String country;
//
//    ITunesProduct(String inputName, String inputYear) {
//        this.name = inputName;
//        this.year = inputYear;
//    }
//}
//
//class ITunesMoviePlayer {
////    // источник: https://www.javatpoint.com/how-to-open-a-file-in-java
////    void playMovie(String searchRequest) throws IOException {
////        playSongInternal(searchRequest, 1);
////    }
////    // конец источника: https://www.javatpoint.com/how-to-open-a-file-in-java
//
//    PageDownloader downloader = new PageDownloader();
//
//    void playMovie(String searchRequest) throws IOException {
//        String url = buildUrl(searchRequest);
//        System.out.println("Will search film by term: " + searchRequest);
//        String page = downloader.downloadWebPage(url);
//
//        String movieName = getTag(page, "trackName");
//        String previewUrl = getTag(page, "previewUrl");
//        String fileExtension = previewUrl.substring(previewUrl.length() - 3);
//        String fileName = movieName + "." + fileExtension;
//        System.out.println("Will download " + movieName);
//        try (InputStream in = new URL(previewUrl).openStream()) {
//            Files.copy(in, Paths.get("src/task5_3_8/" + fileName));
//            File file = new File("src/task5_3_8/" + fileName);
//            if (file.exists()) {
//                Desktop desktop = Desktop.getDesktop();
//                desktop.open(file);
//                System.out.println("Downloaded and opened!");
//            } else {
//                System.out.println("Error: The file " + fileName + " doesn't exist.");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    private String getTag(String page, String tagName) {
////        int start = page.indexOf("artistName") + "artistName".length() + 3;
//        int start = page.indexOf(tagName) + tagName.length() + 3;
//        int end = page.indexOf("\"", start);
//        String value = page.substring(start, end);
//        return value;
//    }
//
//    private String buildUrl(String searchRequest) {
//        int limit = 50;
//        String term = searchRequest.replaceAll(" ", "+");
//        String itunesApi = "https://itunes.apple.com/search?term=";
//        String limitParam = "&limit=1" + limit;
//        String mediaParam = "&media=movie";
//        StringBuilder builder = new StringBuilder();
//        builder.append(itunesApi);
//        builder.append(term);
//        builder.append(limitParam);
//        builder.append(mediaParam);
//        return builder.toString();
//    }
//
//    public String downloadWebPage(String url) throws IOException {
//        StringBuilder result = new StringBuilder();
//        String line;
//        URLConnection urlConnection = new URL(url).openConnection();
//        try (InputStream is = urlConnection.getInputStream();
//             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
//            while ((line = br.readLine()) != null) {
//                result.append(line);
//            }
//        }
//        return result.toString(); // goto 13 line
//    }
//}
//
//class PageDownloader {
//    public String downloadWebPage(String url) /*throws IOException */{
//        StringBuilder result = new StringBuilder();
//        String line;
//        URLConnection urlConnection = null;
//        try {
//            urlConnection = new URL(url).openConnection();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try (InputStream is = urlConnection.getInputStream();
//             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
//            while ((line = br.readLine()) != null) {
//                result.append(line);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return result.toString(); // goto 13 line
//    }
//}
//// КОНЕЦ ПРИМЕРА 4