package ustin.psbot.services.forsite;

import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ustin.psbot.dto.DTOForSaveAndUpdateGame;
import ustin.psbot.models.Game;
import ustin.psbot.dto.DTOToView;
import ustin.psbot.repositories.PSRepository;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
        game.setQuantity(quantity);
        game.setNameOfTheGame(nameOfTheGame);
        game.setLocations(locations);
        game.setPsPlatforms(platform);
        game.setStyle(style);
        game.setFilePath(filePath);

        return game;
    }

    public DTOToView findOneGameByName(String nameOfTheGame) {
        String name = URLDecoder.decode(nameOfTheGame, StandardCharsets.UTF_8);  // получаем имя игры в декодированном виде
        Optional<Game> optionalGame = psRepository.findByNameOfTheGame(name);    // находим в БД игру
        DTOToView psDTOToViewForSite = null;

        if (optionalGame.isPresent()) {
            psDTOToViewForSite = mapper.map(optionalGame.get(), DTOToView.class);
        }
        return psDTOToViewForSite;
    }


    public Page<Game> findAllGames(Pageable pageable) {
        return psRepository.findAll(pageable);
    }

    public DTOToView findGameById(String name) {
        Optional<Game> optionalGame = psRepository.findByNameOfTheGame(name);
        DTOToView DTOToView = null;
        if (optionalGame.isPresent()) {
            DTOToView = mapper.map(optionalGame.get(), DTOToView.class);
        }
        return DTOToView;
    }

    public Game findOneGame(String name) {
        return psRepository.findGamesByNameOfTheGame(name);
    }

//    @SneakyThrows
//    @Transactional
//    public void saveGame(GameDTO dto) {
//        Game game = mapper.map(dto, Game.class);
//
//        String filePath = "D:\\SpringBoot\\PSBot\\src\\main\\resources\\static\\" + dto.getFile().getOriginalFilename();
//        game.setFilePath(filePath);
//
//        Path path = Paths.get("D:\\SpringBoot\\PSBot\\src\\main\\resources\\static\\", dto.getFile().getOriginalFilename()); // Создаем путь к файлу
//        Files.write(path, dto.getFile().getBytes()); // Записываем байты изображения в файл
//
//        psRepository.save(game);
//    }

    @Transactional
    public void saveGame(DTOForSaveAndUpdateGame dto) {
        saveImageFromMultipartFileInStaticFolder(dto.getFile());
        Game game = mapper.map(dto, Game.class);
        game.setFilePath(getFilePath(dto.getFile()).toString());
        game.setLink(createLinkToImage(dto.getFile()));
        psRepository.save(game);
    }

    @SneakyThrows
    @Transactional
    public void updateGame(DTOForSaveAndUpdateGame dto, String name) {
        Game game = psRepository.findGamesByNameOfTheGame(name);

        Map<String, String> map = new HashMap<>();
        map.put("path", game.getFilePath());
        map.put("link", game.getLink());
        map.put("id", String.valueOf(game.getId()));

        MultipartFile file = dto.getFile();
        if (file.getSize() == 0) {
            game = mapper.map(dto, Game.class);
            game.setId(Integer.parseInt(map.get("id")));
            game.setLink(map.get("link"));
            game.setFilePath(map.get("path"));
            psRepository.save(game);
        } else
            if (file.getSize() != 0) {
                Path pathOne = Path.of("D:\\SpringBoot\\PSBot\\src\\main\\resources\\static\\");
                Path filePath = Path.of(game.getFilePath());
                saveImageFromMultipartFileInStaticFolder(dto.getFile());
                deleteFile(String.valueOf(pathOne.resolve(filePath)));
                game = mapper.map(dto, Game.class);
                game.setLink(createLinkToImage(dto.getFile()));
                game.setFilePath(getFilePath(dto.getFile()).toString());
                game.setId(Integer.parseInt(map.get("id")));
                psRepository.save(game);
            }
    }

    @Transactional
    public void deleteGameByName(String name) {
        Game game = psRepository.findGamesByNameOfTheGame(name);
        psRepository.deleteById(game.getId());
        Path path = Path.of("D:\\SpringBoot\\PSBot\\src\\main\\resources\\static\\");
        Path path1 = Path.of(game.getFilePath());
        deleteFile(String.valueOf(path.resolve(path1)));
    }

    public Path getFilePath(MultipartFile file) {
        return Paths.get("images\\", file.getOriginalFilename()); // Создаем путь к файлу
    }

    @SneakyThrows
    public void saveImageFromMultipartFileInStaticFolder(MultipartFile multipartFile) {
        byte[] bytes = multipartFile.getBytes();
        Path pathOne = Path.of("D:\\SpringBoot\\PSBot\\src\\main\\resources\\static\\");

        Files.write(pathOne.resolve(getFilePath(multipartFile)), bytes);
    }

    @SneakyThrows
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.delete();
    }

    @SneakyThrows
    public byte[] readBytesFromFile(Path filePath) {
        return Files.readAllBytes(filePath);
    }


    @SneakyThrows
    public byte[] readFileAsBytes(String filePath) {
        Path path = Paths.get(filePath);
        byte[] fileContent;
        try {
            fileContent = Files.readAllBytes(path); // Чтение файла в массив байтов
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0]; // Возвращаем пустой массив в случае ошибки
        }
    }
    public String createLinkToImage(MultipartFile file) {
        return  "/images/" + file.getOriginalFilename();
    }
}

