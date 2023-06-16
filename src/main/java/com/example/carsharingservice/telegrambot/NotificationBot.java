package com.example.carsharingservice.telegrambot;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class NotificationBot extends TelegramLongPollingBot {
    private final UserRepository userRepository;

    public NotificationBot(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            if (messageText.equals("/start")) {
                sendGreetMessage(chatId, update.getMessage().getChat().getFirstName());
            } else {
                Optional<User> userByEmail = userRepository.getUserByEmail(messageText);
                if (userByEmail.isPresent()) {
                    User user = userByEmail.get();
                    user.setChatId(chatId);
                    userRepository.save(user);
                    sendThankYouMessage(chatId);
                } else {
                    sendFailMessage(chatId);
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

    private void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can't sent message", e);
        }
    }

    private void sendGreetMessage(Long chatId, String name) {
        String text = "Hello user: " + name + ", Please send your email";
        sendMessage(chatId, text);
    }

    private void sendFailMessage(Long chatId) {
        String text = "User with this email doesn't exist in DB, please check your credential";
        sendMessage(chatId, text);
    }

    private void sendThankYouMessage(Long chatId) {
        String text = "You are successfully sync with your account";
        sendMessage(chatId, text);
    }
}
