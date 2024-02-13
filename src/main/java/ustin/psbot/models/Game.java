package ustin.psbot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int id;

    @Column(name = "name_of_the_game")
    private String nameOfTheGame;

    @Column(name = "image_content_type")
    private String contentType;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "location")
    private String locations;

    private String psPlatforms;

    @Lob
    @Column(name = "image")
    private byte[] bytes;

    @Column(name = "suffix")
    private String suffix;
}
