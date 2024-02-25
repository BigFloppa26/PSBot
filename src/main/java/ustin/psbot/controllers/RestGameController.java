package ustin.psbot.controllers;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ustin.psbot.dto.PSGameDTOForSite;
import ustin.psbot.services.forsite.WebService;

import java.io.*;

@Log
@RestController
@RequestMapping("/games")
public class RestGameController {
//    private final WebService webService;
//    private final ResourceLoader loader;
//
//    @Autowired
//    public RestGameController(WebService webService, ResourceLoader loader) {
//        this.webService = webService;
//        this.loader = loader;
//    }
//
//    @SneakyThrows
//    @PostMapping
//    public ResponseEntity<?> saveGame(@RequestParam("file") MultipartFile file,
//                                      @RequestParam("quantity") int quantity,
//                                      @RequestParam("location") String location,
//                                      @RequestParam("platform") String platform,
//                                      @RequestParam("nameofthegame") String nameOfTheGame,
//                                      @RequestParam("style") String style) {
//        String fileName = file.getOriginalFilename();
//        String filePath = "D:\\SpringBoot\\PSBot\\src\\main\\resources\\static\\" + fileName;
//
//        try {
//
//            File newFile = new File(filePath);
//            newFile.createNewFile();
//
//            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
//            FileCopyUtils.copy(file.getInputStream(), fileOutputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (webService.saveGame(file, quantity, location, platform, nameOfTheGame, style, filePath)) {
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
//
//    @GetMapping("/{nameofthegame}")
//    @ResponseBody
//    public ResponseEntity<PSGameDTOForSite> showOneGame(@PathVariable("nameofthegame") String nameOfTheGame) {
//        PSGameDTOForSite psGameDTOForSite = webService.findOneGameByName(nameOfTheGame);
//
//        if (psGameDTOForSite != null) {
//            return ResponseEntity.status(HttpStatus.OK).body(psGameDTOForSite);
//        } else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @DeleteMapping("/{nameofthegame}")
//    public ResponseEntity<?> deleteGame(@PathVariable("nameofthegame") String nameOfTheGame) {
//        return new ResponseEntity<>(webService.deleteGameByName(nameOfTheGame));
//    }
//
//    @SneakyThrows
//    @PostMapping("/saveimage")
//    public ResponseEntity<?> saveImage(@RequestParam("photo") MultipartFile file) {        // сохраняет файл в статик ресурсах
//
//        return ResponseEntity.status(HttpStatus.OK).body("OK");
//    }
}
