package ustin.psbot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Entity(name = "game")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name_of_the_game")
    @NotEmpty(message = "Plz write name of the game")
    private String nameOfTheGame;

    @Column(name = "location")
    @NotEmpty(message = "Plz add location")
    private String locations;

    @Column(name = "quantity")
    @Min(0)
    private int quantity;

    @Column(name = "ps_platforms")
    @NotEmpty
    private String psPlatforms;

    @Column(name = "style")
    private String style;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "link_to_image")
    private String link;
}
