package com.example.carsharingservice.telegrambot;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class NotificationBot extends TelegramLongPollingBot {
    private final UserService userService;

    public NotificationBot(UserService userService) {
        super();
        this.userService = userService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            if (messageText.equals("/start")) {
                greetMessage(chatId, update.getMessage().getChat().getFirstName());
            } else {
                User user = userService.getByUsername(messageText);
                if (user != null) {
                    user.setChatId(chatId);
                    userService.save(user);
                    thankYouMessage(chatId);
                } else {
                    failMessage(chatId);
                }
            }
        }
    }

    @Override
    public String getBotToken() {
        Dotenv dotenv = Dotenv.load();
        return dotenv.get("YOUR_BOT_TOKEN");
    }

    @Override
    public String getBotUsername() {
        Dotenv dotenv = Dotenv.load();
        return dotenv.get("YOUR_BOT_NAME");
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

    private void thankYouMessage(Long chatId) {
        String text = "You are successfully sync with your account";
        sentMessage(chatId, text);
    }
}
