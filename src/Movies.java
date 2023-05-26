
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class Movies {
    private int id;
    private String title;
    private int releaseYear;
    private String director;
    private String genre;
    private String plotSummary;
    private String urlImage;
    private String price;



    public Movies(int id, String title, int releaseYear, String director, String genre, double price, String plotSummary) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.genre = genre;
        this.price = String.valueOf(price);
        this.plotSummary = plotSummary;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public void addMovie() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:/oop2final/onlineMovieBooking.db", "username", "password");
            String query = "INSERT INTO movie (title, release_year, director, genre, plot_summary, price,  url_image) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setInt(2, releaseYear);
            statement.setString(3, director);
            statement.setString(4, genre);
            statement.setString(5, plotSummary);
            statement.setString(6, price);
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
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:/oop2final/onlineMovieBooking.db", "username", "password");
            String query = "UPDATE movie SET title = ?, release_year = ?, director = ?, genre = ?, plot_summary = ?, price =?, url_image = ? WHERE movie_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setInt(2, releaseYear);
            statement.setString(3, director);
            statement.setString(4, genre);
            statement.setString(5, plotSummary);
            statement.setDouble(6, Double.parseDouble(price));
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
}
