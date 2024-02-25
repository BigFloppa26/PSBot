package ustin.psbot.controllers;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ustin.psbot.models.Game;
import ustin.psbot.models.GameDTO;
import ustin.psbot.services.forsite.WebService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("games")
public class GameController {
    private final WebService webService;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    public GameController(WebService webService) {
        this.webService = webService;
    }

    @GetMapping
    public String showAllGames(Model model,
                               @RequestParam(value = "page", required = false) Optional<Integer> page,
                               @RequestParam(value = "size", required = false) Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);

        Page<Game> gameList = webService.findAllGames(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("pageList", gameList);

        return "/games/allGames";
    }

    @GetMapping("/{name}")
    public String showOneGame(Model model, @PathVariable("name") String name) {
        model.addAttribute("game", webService.findOneGame(name));

        return "/games/oneGame";
    }

    @GetMapping("/addGame")
    public String pageForSaveGame(Model model, @ModelAttribute("gameDto") GameDTO gameDTO) {
        model.addAttribute("newGame", gameDTO);
        return "/games/saveGame";
    }

    @PostMapping("/addGame")
    public String saveGame(@ModelAttribute("newGame") GameDTO dto, BindingResult bindingResult) {

        webService.saveGame(dto);
        return "redirect:/allGames";
    }

    @GetMapping("/image")
    public String pageImage() {
        return "/games/image";
    }

    @SneakyThrows
    @PostMapping("/image")
    public String uploadImage(Model model, @RequestParam("image") MultipartFile multipartFile) {

        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get("D:\\SpringBoot\\PSBot\\src\\main\\resources\\static", multipartFile.getOriginalFilename());
        fileNames.append(multipartFile.getOriginalFilename());
        Files.write(fileNameAndPath, multipartFile.getBytes());
        System.out.println(multipartFile.getOriginalFilename());
        model.addAttribute("msg", "Uploaded images: " + fileNames);

        return "/games/image";
    }
}
