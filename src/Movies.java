import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Movies {
    private int id;
    private String title;
    private int releaseYear;
    private String director;
    private String genre;
    private String plotSummary;
    private String urlImage;
    private double price;

    public Movies(int id, String title, int releaseYear, String director, String genre, double price, String plotSummary, String urlImage) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.genre = genre;
        this.price = price;
        this.plotSummary = plotSummary;
        this.urlImage = urlImage;
    }

    // Getters and setters

    public Movies() {

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = Double.parseDouble(price);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public String getGenre() {
        return genre;
    }

    public String getPlotSummary() {
        return plotSummary;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPlotSummary(String plotSummary) {
        this.plotSummary = plotSummary;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public static class MovieData {
        private String title;
        private String urlImage;

        public MovieData(String title, String urlImage) {
            this.title = title;
            this.urlImage = urlImage;
        }

        public String getTitle() {
            return title;
        }

        public String getUrlImage() {
            return urlImage;
        }
    }

    public void addMovie() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite3/OOP2/onlineMovieBooking.db", "username", "password");
            String query = "INSERT INTO movie (title, release_year, director, genre, plot_summary, price,  url_image) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setInt(2, releaseYear);
            statement.setString(3, director);
            statement.setString(4, genre);
            statement.setString(5, plotSummary);
            statement.setDouble(6, price);
            statement.setString(7, urlImage);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Movie added to the database.");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMovie() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite3/OOP2/onlineMovieBooking.db", "username", "password");
            String query = "UPDATE movie SET title = ?, release_year = ?, director = ?, genre = ?, plot_summary = ?, price =?, url_image = ? WHERE movie_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setInt(2, releaseYear);
            statement.setString(3, director);
            statement.setString(4, genre);
            statement.setString(5, plotSummary);
            statement.setDouble(6, price);
            statement.setString(7, urlImage);
            statement.setInt(8, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Movie updated in the database.");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> retrieveMoviePostersFromDatabase() {
        List<String> moviePosters = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite3/OOP2/onlineMovieBooking.db", "username", "password");
            String query = "SELECT url_image FROM movie";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String urlImage = resultSet.getString("url_image");
                moviePosters.add(urlImage);
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return moviePosters;
    }

    public Movies retrieveMovieDetailsFromDatabase(String imageUrl) {
        Movies movie = null;

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite3/OOP2/onlineMovieBooking.db", "username", "password");
            String query = "SELECT * FROM movie WHERE url_image = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, imageUrl);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Extract the movie details from the result set
                int id = resultSet.getInt("movie_id");
                String title = resultSet.getString("title");
                int releaseYear = resultSet.getInt("release_year");
                String director = resultSet.getString("director");
                String genre = resultSet.getString("genre");
                double price = resultSet.getDouble("price");
                String plotSummary = resultSet.getString("plot_summary");

                // Create a new Movies object with the retrieved details
                movie = new Movies(id, title, releaseYear, director, genre, price, plotSummary, imageUrl);
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movie;
    }

    public static List<MovieData> retrieveMovieDataFromDatabase() {
        List<MovieData> movieDataList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite3/OOP2/onlineMovieBooking.db", "username", "password");
            String query = "SELECT * FROM movie";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("movie_id");
                String title = resultSet.getString("title");
                int releaseYear = resultSet.getInt("release_year");
                String director = resultSet.getString("director");
                String genre = resultSet.getString("genre");
                double price = resultSet.getDouble("price");
                String plotSummary = resultSet.getString("plot_summary");
                String urlImage = resultSet.getString("url_image");

                // Create a new MovieData object with the retrieved data
                MovieData movieData = new MovieData(title, urlImage);
                movieDataList.add(movieData);
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movieDataList;
    }
}
