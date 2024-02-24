package ustin.psbot.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ustin.psbot.models.Game;
import ustin.psbot.services.forsite.WebService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("games")
public class GameController {
    private final WebService webService;

    public GameController(WebService webService) {
        this.webService = webService;
    }

    @GetMapping
    public String showAllGames(Model model,
                               @RequestParam(value = "page", required = false) Optional<Integer> page,
                               @RequestParam(value = "size", required = false) Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);

        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Game> gameList = webService.findAllGames(pageable);
        model.addAttribute("pageList", gameList);

        return "/games/allGames";
    }

    @GetMapping("/{id}")
    public String showOneGame(Model model, @PathVariable("id") String name) {
        model.addAttribute("game", webService.findOneGame(name));

        return "/games/oneGame";
    }
}
