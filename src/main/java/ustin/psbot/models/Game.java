package ustin.psbot.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "game")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name_of_the_game")
    private String nameOfTheGame;

    @Column(name = "location")
    private String locations;

    @Lob
    @Column(name = "image")
    private byte[] bytes;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "ps_platforms")
    private String psPlatforms;

    @Column(name = "image_content_type")
    private String contentType;

    @Column(name = "suffix")
    private String suffix;

    @Column(name = "style")
    private String style;

    @Column(name = "file_path")
    private String filePath;
}
