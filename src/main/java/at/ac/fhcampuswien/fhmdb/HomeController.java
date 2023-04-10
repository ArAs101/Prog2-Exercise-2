package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies;
    @FXML
    public Label sortingStatement;

    @FXML
    public JFXComboBox releaseComboBox;

    @FXML
    public JFXComboBox ratingComboBox;

    @FXML
    public JFXButton resetBtn;

    protected ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    protected SortedState sortedState;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeState();
        initializeLayout();
    }

    public void initializeState() {
        //allMovies = Movie.initializeMovies();
        allMovies = MovieAPI.getAllMovies();
        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list
        sortedState = SortedState.NONE;
    }

    public void initializeLayout() {
        movieListView.setItems(observableMovies);   // set the items of the listview to the observable list
        movieListView.setCellFactory(movieListView -> new MovieCell()); // apply custom cells to the listview

        Object[] genres = Genre.values();   // get all genres
        genreComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        genreComboBox.getItems().addAll(genres);    // add all genres to the combobox
        genreComboBox.setPromptText("Filter by Genre");

        Integer[] years = new Integer[78];
        int it = 0;
        for (int i = 1946; i < 2024; i++) {
            years[it] = i;
            it++;
        }
        releaseComboBox.getItems().addAll(years);

        Double[] ratings = new Double [] {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        ratingComboBox.getItems().addAll(ratings);
    }

    // sort movies based on sortedState
    // by default sorted state is NONE
    // afterwards it switches between ascending and descending
    public void sortMovies() {
        if (sortedState == SortedState.NONE || sortedState == SortedState.DESCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle));
            sortedState = SortedState.ASCENDING;
            sortingStatement.setText("Sorting Order: Ascending");
        } else if (sortedState == SortedState.ASCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
            sortedState = SortedState.DESCENDING;
            sortingStatement.setText("Sorting Order: Descending");
        }
    }

    public List<Movie> filterByQuery(List<Movie> movies, String query){
        if(query == null || query.isEmpty()) return movies;

        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie ->
                        movie.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                                movie.getDescription().toLowerCase().contains(query.toLowerCase())
                )
                .toList();
    }

    public List<Movie> filterByGenre(List<Movie> movies, Genre genre){
        if(genre == null) return movies;

        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getGenres().contains(genre))
                .toList();
    }

    public List<Movie> filterByYear(List<Movie> movies, int year){
        if(year < 0) return null;

        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getReleaseYear() == (year))
                .toList();
    }

    public List<Movie> filterByRating(List<Movie> movies, double rating){
        if(rating < 0) return movies;

        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getRating() >= (rating))
                .toList();
    }

    public void applyAllFilters(String searchQuery, Object genre, Integer year, Double rating) {
        List<Movie> filteredMovies = allMovies;

        if (!searchQuery.isEmpty()) {
            filteredMovies = filterByQuery(filteredMovies, searchQuery);
        }

        if (genre != null && !genre.toString().equals("No filter")) {
            filteredMovies = filterByGenre(filteredMovies, Genre.valueOf(genre.toString()));
        }

        if (year != null) {
            filteredMovies = filterByYear(filteredMovies, year);
        }

        if (rating != null) {
            filteredMovies = filterByRating(filteredMovies, rating);
        }

        observableMovies.clear();
        observableMovies.addAll(filteredMovies);
    }

    public void searchBtnClicked(ActionEvent actionEvent) {
        String searchQuery = searchField.getText().trim().toLowerCase();
        Object genre = genreComboBox.getSelectionModel().getSelectedItem();

        applyAllFilters(searchQuery, genre, (Integer) releaseComboBox.getSelectionModel().getSelectedItem(), (Double) ratingComboBox.getSelectionModel().getSelectedItem());

        if(sortedState != SortedState.NONE) {
            sortMovies();
        }
    }

    public void sortBtnClicked(ActionEvent actionEvent) {
        sortMovies();
    }

    public String getMostPopularActor(List<Movie> movies) {
        Map<String, Integer> mainCastMap = new HashMap<>();
        movies.stream().filter(Objects::nonNull).map(Movie::getMainCast).flatMap(List::stream)
                .forEach(i -> {
                    if (mainCastMap.containsKey(i)) {
                        mainCastMap.put(i, mainCastMap.get(i)+1);
                    }else {
                        mainCastMap.put(i, 1);
                    }
                });
        return mainCastMap.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    public int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream().filter(Objects::nonNull).map(Movie::getTitle).map(String::length).max(Integer::compareTo).get();
    }

    public long countMoviesFrom(List<Movie> movies, String director){
        return movies.stream().filter(Objects::nonNull).filter(movie -> movie.getDirectors().contains(director)).count();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream().filter(Objects::nonNull).filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }

    public void resetBtnClicked(ActionEvent actionEvent) {
        searchField.setText("");
        genreComboBox.setValue(null);
        releaseComboBox.setValue(null);
        ratingComboBox.setValue(null);

        initializeState();
    }
}