package ustin.psbot.utils;

import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PSUtils {

    public static SendMessage startMarkup(Long chatId) {
        InlineKeyboardButton range = new InlineKeyboardButton("Весь ассортимент");
        InlineKeyboardButton inStock = new InlineKeyboardButton("В наличии");

        range.setCallbackData("/range");
        inStock.setCallbackData("/inStock");

        List<InlineKeyboardButton> buttonList = new ArrayList<>();
        {
            buttonList.add(range);
            buttonList.add(inStock);
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Выберите действие: ");
        sendMessage.setReplyMarkup(new InlineKeyboardMarkup(Collections.singletonList(buttonList)));
        return sendMessage;
    }

    public static SendPhoto sendPhoto(Long chatId, InputFile image) {

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(image);

        return sendPhoto;
    }

    public static SendMessage sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);

        return sendMessage;
    }

    @SneakyThrows
    public static InputFile convertBytesToInputFile(byte[] bytes, String prefix, String suffix) {
        File file = File.createTempFile(prefix, suffix);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.close();

        return new InputFile(file);
    }
}

