package ustin.psbot.services;

import com.vdurmont.emoji.EmojiParser;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import ustin.psbot.dto.PSGameDTOForBot;
import ustin.psbot.services.forbot.BotService;
import ustin.psbot.utils.PSUtils;
import ustin.psbot.utils.bot.Buttons;
import ustin.psbot.utils.bot.ReplyKeyboard;

import java.io.File;
import java.util.List;

import static com.vdurmont.emoji.EmojiParser.parseToUnicode;

@Getter
@Setter
@Log
@Component
public class PSBot extends TelegramLongPollingBot {
    private final BotService botService;
    private String address = null;
    private String style = null;
    private String platform = null;
    private Long chatId;
    private int messageId;
    private final ReplyKeyboard replyKeyboard;
    private final PSUtils psUtils;
    private final Buttons buttons;

    @Autowired
    public PSBot(@Value("${bot.token}") String token, BotService botService, ReplyKeyboard replyKeyboard, PSUtils psUtils, Buttons buttons) {
        super(token);
        this.botService = botService;
        this.replyKeyboard = replyKeyboard;
        this.psUtils = psUtils;
        this.buttons = buttons;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            this.chatId = update.getMessage().getChatId();
            execute(psUtils.sendMessageWithMarkup(chatId, replyKeyboard.startReplyKeyboardMarkup(), "Привет! Куда двигаемся?")); // отправляем стартовую клавиатуру
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            this.chatId = update.getMessage().getChatId();
            this.messageId = update.getMessage().getMessageId();

            String message = update.getMessage().getText();
            message = EmojiParser.removeAllEmojis(message).trim();

            switch (message) {   // первый выбор после кнопки Старт (1)
                case "Игры": {
                    execute(psUtils.sendMessageWithMarkup(chatId, replyKeyboard.keyboardForChoosePlatform(), "Под какую приставку фильтруем?"));
                    break;
                }
                case "Комплектующие": {
                    File file = new File("D:\\SpringBoot\\PSBot\\src\\main\\resources\\static\\Bloodborne.jpg");
                    InputFile inputFile = new InputFile();
                    inputFile.setMedia(file);
                    execute(SendPhoto.builder().chatId(chatId).photo(inputFile).build());
                    break;
                }
                case "Ремонт": {
                    System.out.println("Ремонт");
                    break;
                }
                case "Наши адреса": {
                    execute(psUtils.sendMessageWithMarkup(chatId, replyKeyboard.keyboardForViewingAddresses(), "Ниже можно посмотреть время работы магазинов"));
                    break;
                }
            }
            switch (message) {      // второй выбор платформы ПС (1-2)
                case "PS3": {
                    this.platform = message;
                    System.out.println(platform);
                    execute(psUtils.sendMessageWithMarkup(chatId, replyKeyboard.keyboardForChooseAddresses(), "В каком магазине смотрим наличие?"));
                    break;
                }
                case "PS4", "PS5": {
                    this.platform = message;
                    execute(psUtils.sendMessageWithMarkup(chatId, replyKeyboard.keyboardForChooseAddresses(), "В каком магазине смотрим наличие?"));
                    break;
                }
            }
            switch (message) {      // третий выбор магазина для просмотра (1-3)
                case "Enter", "Aurora": {
                    this.address = message.toUpperCase();
                    execute(psUtils.sendMessageWithMarkup(chatId, replyKeyboard.keyboardToSelectGenre(), "В какие жанры играете?"));
                    break;
                }
            }
            switch (message) {      // четвёртый выбор жанра игр (1-4)
                case "Souls", "Прочие", "Стратегии", "Файтинги", "Аркады", "Приключения", "Шутеры", "Гонки": {
                    this.style = message;
                    List<PSGameDTOForBot> gameList = botService.findGamesByPlatformsLocationAndStyle(platform, address, style.toUpperCase());
                    for (int i = 0; i < gameList.size(); i++) {
                        execute(SendPhoto.builder().chatId(chatId).photo(gameList.get(i).getFile()).build());
                        execute(SendMessage.builder().chatId(chatId).text(gameList.get(i).getNameOfTheGame()).build());
                    }
                    break;
                }
            }

            switch (message) {      // Отправка графика работы магазинов (4)
                case "ТЦ Интер": {
                    execute(buttons.sendEnterAddress(chatId));
                    break;
                }
                case "ТЦ Аврора": {
                    execute(buttons.sendAuroraAddress(chatId));
                    break;
                }
            }

            switch (message) {      // блок со всеми возвратами назад
                case "Назад к выбору платформы": {
                    execute(psUtils.sendMessageWithMarkup(chatId, replyKeyboard.keyboardForChoosePlatform(), "Под какую приставку фильтруем?"));
                    break;
                }
                case "Назад в начало": {
                    execute(psUtils.sendMessageWithMarkup(chatId, replyKeyboard.startReplyKeyboardMarkup(), "Привет! Куда двигаемся?")); // отправляем стартовую клавиатуру
                    break;
                }
                case "Назад к выбору жанров": {
                    break;
                }
                case "Назад к выбору магазина": {
                    execute(psUtils.sendMessageWithMarkup(chatId, replyKeyboard.keyboardForChooseAddresses(), "В каком магазине удобно смотрим наличие?"));
                    break;
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "PSBot";
    }
}

