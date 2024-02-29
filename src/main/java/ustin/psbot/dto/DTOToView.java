package ustin.psbot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class DTOToView {

    @NotEmpty(message = "Plz write name of the game")
    private String nameOfTheGame;

    @NotEmpty(message = "Plz write location of the game")
    private String locations;

    @Min(value = 1, message = "Plz add quantity for game")
    private int quantity;

    @NotEmpty(message = "Plz write platform of the game")
    private String psPlatforms;

    @NotEmpty(message = "Plz write style of the game")
    private String style;

    private MultipartFile multipartFile;
}
