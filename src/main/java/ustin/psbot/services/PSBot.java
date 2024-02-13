package ustin.psbot.services;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import ustin.psbot.models.Game;
import ustin.psbot.utils.PSUtils;

@Log
@Component
public class PSBot extends TelegramLongPollingBot {
    private final GameService gameService;

    @Autowired
    public PSBot(@Value("${bot.token}") String token, GameService gameService) {
        super(token);
        this.gameService = gameService;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            execute(PSUtils.sendMessage(chatId,"Welcome, " + update.getMessage().getChat().getFirstName())); // отправляем приветственное сообщение
            execute(PSUtils.startMarkup(chatId)); // отправляем стартовую клавиатуру
        }
        else if (update.hasCallbackQuery()) {
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            String query = update.getCallbackQuery().getData();
            switch (query) {
                case "/range": {

                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "PSBot";
    }
}
