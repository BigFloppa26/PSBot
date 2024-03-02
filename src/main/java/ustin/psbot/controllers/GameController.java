package ustin.psbot.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ustin.psbot.dto.DTOForSaveAndUpdateGame;
import ustin.psbot.models.Game;
import ustin.psbot.dto.DTOToView;
import ustin.psbot.services.forsite.WebService;

import java.util.Optional;
import java.util.Random;

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
        int pageSize = size.orElse(25);

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
    public String pageForSaveGame(Model model) {
        model.addAttribute("newGame", new DTOForSaveAndUpdateGame());
        return "/games/saveGame";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute("newGame") @Valid DTOForSaveAndUpdateGame dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/games/saveGame";
        }
        webService.saveGame(dto);

        return "redirect:/games";
    }

    @GetMapping("/{name}/update")
    public String pageForUpdateGame(@PathVariable("name") String name, Model model, HttpServletResponse response) {
        model.addAttribute("updateGame", webService.findOneGameByName(name));

        return "/games/update";
    }

    @PatchMapping("/{name}/update")
    public String updateGame(@PathVariable("name") String name, @ModelAttribute("updateGame") @Valid DTOForSaveAndUpdateGame game,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/games/update";
        }
        webService.updateGame(game, name);

        return "redirect:/games";
    }

    @DeleteMapping("{name}/delete")
    public String deleteGame(@PathVariable("name") String name) {
        webService.deleteGameByName(name);
        return "redirect:/games";
    }
}
