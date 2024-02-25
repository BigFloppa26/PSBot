package ustin.psbot.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {

    private String nameOfTheGame;
    private String locations;
    private File file;
    private int quantity;
    private String psPlatforms;
    private String style;

}
