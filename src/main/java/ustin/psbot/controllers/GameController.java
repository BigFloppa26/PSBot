package ustin.psbot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ustin.psbot.dto.PSGameDTOForSite;
import ustin.psbot.services.GameService;

import java.io.IOException;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<?> saveGame(@RequestParam("photo") MultipartFile multipartFile,
                                      @RequestParam("quantity") int quantity,
                                      @RequestParam("location") String location,
                                      @RequestParam("platform") String platform,
                                      @RequestParam("nameofthegame") String nameOfTheGame) throws IOException {
        if(gameService.saveGame(multipartFile, quantity, location, platform, nameOfTheGame)) {
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Save has error");
    }

    @GetMapping("/{nameofthegame}")
    @ResponseBody
    public ResponseEntity<PSGameDTOForSite> showOneGame(@PathVariable("nameofthegame") String nameOfTheGame) {
        PSGameDTOForSite psGameDTOForSite = gameService.findOneGameByName(nameOfTheGame);

        if (psGameDTOForSite != null) {
            return ResponseEntity.status(HttpStatus.OK).body(psGameDTOForSite);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
