package ustin.psbot.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ustin.psbot.dto.PSGameDTOForSite;
import ustin.psbot.models.Game;
import ustin.psbot.repositories.PSRepository;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GameService {
    private final PSRepository psRepository;
    private final ModelMapper mapper;

    @Autowired
    public GameService(PSRepository psRepository, ModelMapper mapper) {
        this.psRepository = psRepository;
        this.mapper = mapper;
    }

    @Transactional
    public boolean saveGame(MultipartFile file, int quantity, String  locations, String  platform, String nameOfTheGame) throws IOException {
        Game game;

        if (file.getSize() != 0) {
            game = convertMultipartToGame(file, quantity, locations, platform, nameOfTheGame);
            psRepository.save(game);
            return true;
        } else
            return false;
    }

    public Game convertMultipartToGame(MultipartFile file, int quantity, String  locations, String  platform, String nameOfTheGame) throws IOException { // сохраняет фото с метаданными
        String fileName = file.getOriginalFilename();    // получаем оригинальное имя для блока ниже
        String fileExtension = null;

        if (file.getSize() != 0) {
            if (fileName != null && !fileName.isEmpty()) {                            // Проверка, что у файла есть имя
                int lastIndex = fileName.lastIndexOf('.');                        // Находим последнюю точку в имени файла

                if (lastIndex != -1 && lastIndex < fileName.length() - 1) {           // Проверка, что точка есть в имени файла и не является последним символом

                    fileExtension = fileName.substring(lastIndex + 1);      // Получаем расширение файла

                }
            }
        }
        Game game = new Game();                           // заполняем объект от клиента
        game.setSuffix(fileExtension);
        game.setQuantity(quantity);
        game.setContentType(file.getContentType());
        game.setNameOfTheGame(nameOfTheGame);
        game.setLocations(locations);
        game.setPsPlatforms(platform);
        game.setBytes(file.getBytes());

        return game;
    }

    public PSGameDTOForSite findOneGameByName(String nameOfTheGame) {
        String name = URLDecoder.decode(nameOfTheGame, StandardCharsets.UTF_8);  // получаем имя игры в декодированном виде
        Optional<Game> optionalGame = psRepository.findByNameOfTheGame(name);    // находим в БД игру

        PSGameDTOForSite psGameDTOForSite = null;
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            psGameDTOForSite = mapper.map(game, PSGameDTOForSite.class);         // переводим объект Game из БД в объект ДТО

            Base64.Encoder base64 = Base64.getEncoder();
            psGameDTOForSite.setImage(base64.encodeToString(optionalGame.get().getBytes()));    // кодируем байты для передачи в ДТО
        }
        return psGameDTOForSite;
    }
}
