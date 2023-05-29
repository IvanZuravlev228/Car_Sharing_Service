package com.example.carsharingservice.telegrambot;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class NotificationBot extends TelegramLongPollingBot {
    Dotenv dotenv = Dotenv.load();

    @Override
    public void onUpdateReceived(Update update) {
        // TODO: Some logic how to handle message from users
    }

    @Override
    public String getBotUsername() {
        return dotenv.get("YOUR_BOT_NAME");
    }

    @Override
    public String getBotToken() {
        return dotenv.get("YOUR_BOT_TOKEN");
    }

    public void sendMessageToChat(String chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can't send message to telegram ", e);
        }
    }
}
