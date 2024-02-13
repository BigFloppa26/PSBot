package ustin.psbot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class PSGameDTOForSite {
    @NotEmpty(message = "Pls write name of the games")
    private String nameOfTheGame;
    private String location;
    private String image;
    @Min(0)
    private int quantity;
    @NotEmpty
    private String platform;
}
