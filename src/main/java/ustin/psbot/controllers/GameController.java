package ustin.psbot.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ustin.psbot.dto.PSGameDTOForSite;
import ustin.psbot.services.GameService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;
    private final ResourceLoader loader;

    @Autowired
    public GameController(GameService gameService, ResourceLoader loader) {
        this.gameService = gameService;
        this.loader = loader;
    }

    @PostMapping
    public ResponseEntity<?> saveGame(@RequestParam("photo") MultipartFile multipartFile,
                                      @RequestParam("quantity") int quantity,
                                      @RequestParam("location") String location,
                                      @RequestParam("platform") String platform,
                                      @RequestParam("nameofthegame") String nameOfTheGame) throws IOException {
        if (gameService.saveGame(multipartFile, quantity, location, platform, nameOfTheGame)) {
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

    @DeleteMapping("/{nameofthegame}")
    public ResponseEntity<?> deleteGame(@PathVariable("nameofthegame") String nameOfTheGame) {
        return new ResponseEntity<>(gameService.deleteGameByName(nameOfTheGame));
    }

    @SneakyThrows
    @PostMapping("/saveimage")
    public ResponseEntity<?> saveImage(@RequestParam("photo") MultipartFile file) {        // сохраняет файл в статик ресурсах
        String filePath = "D:/SpringBoot/PSBot/src/main/resources/static/" + file.getOriginalFilename();
        File newFile = new File(filePath);

        try (FileOutputStream fileOutputStream = new FileOutputStream(newFile)) {
            FileCopyUtils.copy(file.getInputStream(), fileOutputStream);
        }
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
}
