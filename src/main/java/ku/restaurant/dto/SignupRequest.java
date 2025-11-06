package ku.restaurant.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SignupRequest {
    @NotBlank(message = "Username is mandatory")
    @Size(min=4, message = "Username must be at least 4 characters in length")
    private String username;


    @NotBlank(message = "Password is mandatory")
    @Size(min=8, message = "Password must be at least 8 characters in length")
    private String password;


    @NotBlank(message = "Name is mandatory")
    @Pattern(regexp = "^[a-zA-Z]+$",
            message = "Name can only contain letters")
    private String name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
