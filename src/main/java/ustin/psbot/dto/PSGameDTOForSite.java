package ustin.psbot.dto;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PSGameDTOForSite {
    private String nameOfTheGame;
    private String locations;
    private byte[] bytes;
    private int quantity;
    private String psPlatforms;
    private String style;
}
