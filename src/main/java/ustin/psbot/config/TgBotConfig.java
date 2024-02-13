package ustin.psbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ustin.psbot.services.PSBot;

@Configuration
public class TgBotConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(PSBot psBot) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(psBot);
        return telegramBotsApi;
    }
}
