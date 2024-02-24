package ustin.psbot.services.forbot;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ustin.psbot.dto.PSGameDTOForBot;
import ustin.psbot.models.Game;
import ustin.psbot.repositories.PSRepository;
import ustin.psbot.utils.PSUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BotService {
    private final PSRepository psRepository;
    private final ModelMapper mapper;
    private final PSUtils psUtils;

    @Autowired
    public BotService(PSRepository psRepository, ModelMapper mapper, PSUtils psUtils) {
        this.psRepository = psRepository;
        this.mapper = mapper;
        this.psUtils = psUtils;
    }
    // в боте видим фото, название,
    public List<PSGameDTOForBot> findGamesByPlatformsLocationAndStyle(String platform, String location, String style) {
        List<Game> games = psRepository.findGamesByPsPlatformsIgnoreCaseAndLocationsIgnoreCaseAndStyleIgnoreCase(platform, location, style);
        List<PSGameDTOForBot> listDto = new ArrayList<>();
        for(Game game : games) {
            PSGameDTOForBot dto = new PSGameDTOForBot();
            dto.setNameOfTheGame(game.getNameOfTheGame());

            File file = new File(game.getFilePath());
            InputFile inputFile = new InputFile(file);

            dto.setFile(inputFile);

            listDto.add(dto);
        }
        return listDto;
    }
}
