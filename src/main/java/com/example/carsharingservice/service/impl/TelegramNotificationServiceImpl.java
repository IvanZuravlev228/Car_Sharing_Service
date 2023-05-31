package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.service.NotificationService;
import com.example.carsharingservice.telegrambot.NotificationBot;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class TelegramNotificationServiceImpl implements NotificationService {
    private final NotificationBot notificationBot;

    @Override
    public void sentMessage(String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        try {
            notificationBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can't sent message to Chat bot");
        }
    }

    @Scheduled(cron = "0 0 12 * * ?")
    @Override
    public void checkOverdueRentals() {
        // TODO: some logic how to handle overdue rental - implements when repository will exist
    }
}
