package ustin.psbot.utils.bot;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
public class ReplyKeyboard {
    public ReplyKeyboardMarkup startReplyKeyboardMarkup() {
        KeyboardButton button1 = new KeyboardButton("Игры " + EmojiParser.parseToUnicode(":video_game:"));
        KeyboardButton button2 = new KeyboardButton("Комплектующие " + EmojiParser.parseToUnicode(":gear:"));
        KeyboardButton button3 = new KeyboardButton("Ремонт " + EmojiParser.parseToUnicode(":hammer_and_wrench:"));
        KeyboardButton button4 = new KeyboardButton("Наши адреса " + EmojiParser.parseToUnicode(":footprints:"));

        KeyboardRow row1 = new KeyboardRow(3);
        row1.addAll(List.of(button1, button2, button3));

        KeyboardRow row2 = new KeyboardRow(1);
        row2.add(button4);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(List.of(row1, row2));
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setIsPersistent(true);

        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup keyboardForChoosePlatform() {
        KeyboardButton button1 = new KeyboardButton("PS3");
        KeyboardButton button2 = new KeyboardButton("PS4");
        KeyboardButton button3 = new KeyboardButton("PS5");
        KeyboardButton button4 = new KeyboardButton("Назад в начало");

        KeyboardRow row1 = new KeyboardRow(4);
        row1.addAll(List.of(button1, button2, button3));

        KeyboardRow row2 = new KeyboardRow(1);
        row2.add(button4);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(List.of(row1, row2));
        replyKeyboardMarkup.setIsPersistent(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;
    }
    public ReplyKeyboardMarkup keyboardForViewingAddresses() {
        KeyboardButton button1 = new KeyboardButton("ТЦ Интер");
        KeyboardButton button2 = new KeyboardButton("ТЦ Аврора");
        KeyboardButton button3 = new KeyboardButton("Назад в начало");

        KeyboardRow row1 = new KeyboardRow(2);
        row1.addAll(List.of(button1, button2));

        KeyboardRow row2 = new KeyboardRow(1);
        row2.add(button3);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(List.of(row1, row2));
        replyKeyboardMarkup.setIsPersistent(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup keyboardForChooseAddresses() {
        KeyboardButton button1 = new KeyboardButton("Enter");
        KeyboardButton button2 = new KeyboardButton("Aurora");
        KeyboardButton button3 = new KeyboardButton("Назад к выбору платформы");

        KeyboardRow row1 = new KeyboardRow(2);
        row1.addAll(List.of(button1, button2));

        KeyboardRow row2 = new KeyboardRow(1);
        row2.add(button3);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(List.of(row1, row2));
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setIsPersistent(true);

        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup keyboardToSelectGenre() {
        KeyboardButton button1 = new KeyboardButton("Шутеры");
        KeyboardButton button2 = new KeyboardButton("Souls");
        KeyboardButton button3 = new KeyboardButton("Приключения");
        KeyboardButton button4 = new KeyboardButton("Аркады");
        KeyboardButton button5 = new KeyboardButton("Файтинги");
        KeyboardButton button6 = new KeyboardButton("Гонки");
        KeyboardButton button7 = new KeyboardButton("Стратегии");
        KeyboardButton button8 = new KeyboardButton("Прочие");
        KeyboardButton button9 = new KeyboardButton("Назад к выбору магазина");


        KeyboardRow row1 = new KeyboardRow(3);
        row1.addAll(List.of(button1, button2, button3));

        KeyboardRow row2 = new KeyboardRow(3);
        row2.addAll(List.of(button4, button5, button6));

        KeyboardRow row3 = new KeyboardRow(3);
        row3.addAll(List.of(button7, button8, button9));

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(List.of(row1, row2, row3));
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;
    }
}
