package ku.restaurant.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class RestaurantRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Min(value = 1, message="Rating must be at least 1")
    @Max(value = 5, message="Rating cannot exceed 5")
    private double rating;

    @NotBlank(message = "Location is mandatory")
    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
