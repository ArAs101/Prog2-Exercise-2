package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie {
    private final String title;
    private final String description;
    private final List<Genre> genres;

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof Movie other)) {
            return false;
        }
        return this.title.equals(other.title) && this.description.equals(other.description) && this.genres.equals(other.genres);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(
                "Life Is Beautiful",
                "When an open-minded Jewish librarian and his son become victims of the Holocaust, he uses a perfect mixture of will, humor, and imagination to protect his son from the dangers around their camp." ,
                Arrays.asList(Genre.DRAMA, Genre.ROMANCE)));
        movies.add(new Movie(
                "The Usual Suspects",
                "A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat, which begin when five criminals meet at a seemingly random police lineup.",
                Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY)));
        movies.add(new Movie(
                "Puss in Boots",
                "An outlaw cat, his childhood egg-friend, and a seductive thief kitty set out in search for the eggs of the fabled Golden Goose to clear his name, restore his lost honor, and regain the trust of his mother and town.",
                Arrays.asList(Genre.COMEDY, Genre.FAMILY, Genre.ANIMATION)));
        movies.add(new Movie(
                "Avatar",
                "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
                Arrays.asList(Genre.ANIMATION, Genre.DRAMA, Genre.ACTION)));
        movies.add(new Movie(
                "The Wolf of Wall Street",
                "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.",
                Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.BIOGRAPHY)));
        movies.add(new Movie(
                "The Dog",
                "Movie about a cute dog.",
                Arrays.asList(Genre.ANIMATION, Genre.FAMILY)
        ));
        movies.add(new Movie(
                "CatsVenture",
                "A cute cat on an adventure.",
                Arrays.asList(Genre.ANIMATION, Genre.ADVENTURE)
        ));
        movies.add(new Movie(
                "M3GAN",
                "Horror movie about a highly intelligent humanoid doll.",
                Arrays.asList(Genre.HORROR, Genre.THRILLER, Genre.DRAMA)
        ));

        movies.add(new Movie(
                "The Godfather",
                "Don Vito Corleone aggressively tries to make his son step in his own footsteps...",
                Arrays.asList(Genre.CRIME, Genre.DRAMA)
        ));

        movies.add(new Movie(
                "John Wick Chapter 4",
                "John Wick uncovers a path to defeating The High Table. But before he can earn his " +
                        "freedom, Wick must face off against a new enemy with powerful alliances across the " +
                        "globe and forces that turn old friends into foes.",
                Arrays.asList(Genre.ACTION, Genre.CRIME, Genre.THRILLER)
        ));

        movies.add(new Movie(
                "Among Us",
                "After the mysterious death of their young son, a couple desperately flees to a remote " +
                        "lake house to escape the unrelenting haunting following them only to discover the mysterious" +
                        " entity is still very much a part of their lives.",
                Arrays.asList(Genre.DRAMA, Genre.HORROR, Genre.THRILLER)
        ));

        movies.add(new Movie(
                "Avatar: The Way of Water",
                "Jake Sully lives with his newfound family formed on the extrasolar moon Pandora. " +
                        "Once a familiar threat returns to finish what was previously started, Jake must work " +
                        "with Neytiri and the army of the Na'vi race to protect their home.",
                Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.FANTASY)
        ));

        return movies;
    }
}