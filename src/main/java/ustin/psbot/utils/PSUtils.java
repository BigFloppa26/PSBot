package ustin.psbot.utils;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.io.File;
import java.io.FileOutputStream;

@Component
public class PSUtils {

    @SneakyThrows
    public InputFile convertBytesToInputFile(byte[] bytes, String prefix, String suffix) {
        File file = File.createTempFile(prefix, suffix);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.close();

        return new InputFile(file);
    }
    public SendPhoto sendPhoto(Long chatId, InputFile image) {

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(image);


        return sendPhoto;
    }
    public SendMessage sendMessageWithMarkup(Long chatId, ReplyKeyboardMarkup replyKeyboardMarkup, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);

        return sendMessage;
    }


}



