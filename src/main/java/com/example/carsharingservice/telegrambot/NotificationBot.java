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
        super(Dotenv.configure().load().get("YOUR_BOT_TOKEN"));
        this.userRepository = userRepository;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            if (messageText.equals("/start")) {
                greetMessage(chatId, update.getMessage().getChat().getFirstName());
            } else {
                Optional<User> userByEmail = userRepository.getUserByEmail(messageText);
                if (userByEmail.isPresent()) {
                    User user = userByEmail.get();
                    user.setChatId(chatId);
                    userRepository.save(user);
                    thankYouMessage(chatId);
                } else {
                    failMessage(chatId);
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return Dotenv.configure().load().get("YOUR_BOT_NAME");
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
