package com.example.carsharingservice.telegrambot;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class NotificationBot extends TelegramLongPollingBot {
    private Dotenv dotenv = Dotenv.load();
    // private final UserService userService;


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            if (messageText.equals("/start")) {
                Long chatId = update.getMessage().getChatId();
                greetMessage(chatId, update.getMessage().getChat().getUserName());
                String userEmail = update.getMessage().getText();
               /* User user = userService.findByEmail(userEmail);
                if (user != null) {
                    user.setChatId(chatId);
                    userService.save(userChat);
                } else {
                    failMessage(chatId);
                } */
            }
        }
    }

    @Override
    public String getBotUsername() {
        return dotenv.get("YOUR_BOT_NAME");
    }

    @Override
    public String getBotToken() {
        return dotenv.get("YOUR_BOT_TOKEN");
    }

    private void sentMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can't sent message");
        }
    }

    private void greetMessage(Long chatId, String name) {
        String text = "Hello user: " + name + ", Please send your email";
        sentMessage(chatId, text);
    }

    private void failMessage(Long chatId) {
        String text = "User with this email doesn't exist in DB, please check your credential";
        sentMessage(chatId, text);
    }
}
