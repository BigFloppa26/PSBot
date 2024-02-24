package ustin.psbot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.InputFile;

@ToString
@Getter
@Setter
public class PSGameDTOForBot {
    @NotEmpty(message = "Pls write name of the games")
    private String nameOfTheGame;
    private InputFile file;
}
