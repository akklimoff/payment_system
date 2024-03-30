package kg.attractor.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "Phone cannot be blank")
    private String phone;

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank
    @Size(min = 8, max = 24, message = "Length must be between 8 and 24")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).+$",
            message = "Password must contain at least one digit, one lowercase and one uppercase letter.")
    private String password;

    private boolean enabled;
}
