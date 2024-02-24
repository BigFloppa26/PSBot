package ustin.psbot.utils.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ustin.psbot.utils.PSUtils;

@Component
public class Buttons {
    private final PSUtils psUtils;
    private final ReplyKeyboard replyKeyboard;

    @Autowired
    public Buttons(PSUtils psUtils, ReplyKeyboard replyKeyboard) {
        this.psUtils = psUtils;
        this.replyKeyboard = replyKeyboard;
    }

    public SendMessage sendEnterAddress(Long chatId) {   // отправляет адрес интера и клаву с выбором адресов
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("""
                    Адрес: Самара ТЦ Enter
                    ул. Стара-Загора, 56
                    тел: 8 937-07-07-511
                    2 Этаж 3 Секция

                    Режим работы: ежедневно
                    с 11:00 до 20:00""");
        sendMessage.setReplyMarkup(replyKeyboard.keyboardForViewingAddresses());

        return sendMessage;
    }

    public SendMessage sendAuroraAddress(Long chatId) {  // отправляет адрес авроры и клаву с выбором адресов
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("""
                Адрес: Самара, Аврора Молл
                ул. Аэродромная, 47А , 0 этаж
                тел: 8 937-07-77-511

                Режим работы: ежедневно
                с 10:00 до 21:00""");
        sendMessage.setReplyMarkup(replyKeyboard.keyboardForViewingAddresses());

        return sendMessage;
    }
}
