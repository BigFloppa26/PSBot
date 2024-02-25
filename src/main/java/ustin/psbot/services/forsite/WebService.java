package ustin.psbot.services.forsite;

import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ustin.psbot.dto.PSGameDTOForSite;
import ustin.psbot.models.Game;
import ustin.psbot.models.GameDTO;
import ustin.psbot.repositories.PSRepository;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class WebService {
    private final PSRepository psRepository;
    private final ModelMapper mapper;

    @Autowired
    public WebService(PSRepository psRepository, ModelMapper mapper) {
        this.psRepository = psRepository;
        this.mapper = mapper;
    }

    @Transactional
    public boolean saveGame(MultipartFile file, int quantity, String locations, String platform, String nameOfTheGame, String style, String filePath) throws IOException {
        Game game;

        if (file.getSize() != 0) {
            game = convertMultipartToGame(file, quantity, locations, platform, nameOfTheGame, style, filePath);
            psRepository.save(game);
            return true;
        } else
            return false;
    }

    public Game convertMultipartToGame(MultipartFile file, int quantity, String locations, String platform, String nameOfTheGame, String style, String filePath) throws IOException { // сохраняет фото с метаданными
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
        game.setStyle(style);
        game.setFilePath(filePath);

        return game;
    }

    public PSGameDTOForSite findOneGameByName(String nameOfTheGame) {
        String name = URLDecoder.decode(nameOfTheGame, StandardCharsets.UTF_8);  // получаем имя игры в декодированном виде
        Optional<Game> optionalGame = psRepository.findByNameOfTheGame(name);    // находим в БД игру
        PSGameDTOForSite psGameDTOForSite = null;

        if (optionalGame.isPresent()) {
            psGameDTOForSite = mapper.map(optionalGame.get(), PSGameDTOForSite.class);
        }
        return psGameDTOForSite;
    }

    @Transactional
    public HttpStatus deleteGameByName(String name) {
        if (psRepository.findByNameOfTheGame(name).isPresent()) {
            psRepository.deleteByNameOfTheGame(name);
            return HttpStatus.OK;
        } else
            return HttpStatus.BAD_REQUEST;
    }

    public Page<Game> findAllGames(Pageable pageable) {
        return psRepository.findAll(pageable);
    }

    public Game findOneGame(String name) {
        return psRepository.findGamesByNameOfTheGame(name);
    }

    @SneakyThrows
    @Transactional
    public void saveGame(GameDTO dto) {
        Game game = mapper.map(dto, Game.class);

        String filePath = "D:\\SpringBoot\\PSBot\\src\\main\\resources\\static\\" + dto.getFile().getName();
        game.setFilePath(filePath);

        try(FileInputStream fis = new FileInputStream(dto.getFile())) {
            byte[] bytes = fis.readAllBytes();
            game.setBytes(bytes);
        }

        game.setContentType("image/jpeg");
        String fileName = dto.getFile().getName();    // получаем оригинальное имя для блока ниже
        String fileExtension = null;


        if (dto.getFile().canRead()) {
            if (fileName != null && !fileName.isEmpty()) {                            // Проверка, что у файла есть имя
                int lastIndex = fileName.lastIndexOf('.');                        // Находим последнюю точку в имени файла
                if (lastIndex != -1 && lastIndex < fileName.length() - 1) {           // Проверка, что точка есть в имени файла и не является последним символом
                    fileExtension = fileName.substring(lastIndex + 1);      // Получаем расширение файла
                }
            }
        }
        game.setSuffix(fileExtension);

        psRepository.save(game);
    }
}

