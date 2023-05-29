package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.service.NotificationService;
import com.example.carsharingservice.telegrambot.NotificationBot;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.scheduling.annotation.Scheduled;

public class TelegramNotificationServiceImpl implements NotificationService {
    private final NotificationBot notificationBot;
    private final Dotenv dotenv = Dotenv.load();

    public TelegramNotificationServiceImpl(NotificationBot notificationBot) {
        this.notificationBot = notificationBot;
    }

    @Override
    public void sentMessage(String message) {
        notificationBot.sendMessageToChat(getChatId(), message);
    }

    @Scheduled(cron = "0 0 12 * * ?")
    @Override
    public void checkOverdueRentals() {
        // TODO: some logic how to handle overdue rental - implements when repository will exist
    }

    private String getChatId() {
        return dotenv.get("CHAT_ID");
    }
}
