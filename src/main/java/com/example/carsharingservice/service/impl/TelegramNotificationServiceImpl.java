package com.example.carsharingservice.service.impl;

import java.time.LocalDate;
import java.util.List;
import com.example.carsharingservice.model.Rental;
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
        LocalDate today = LocalDate.now();
        List<Rental> overdueRentals = rentalRepository.findOverdueRentals(today);

        if (overdueRentals.isEmpty()) {
            sentMessage("No rentals overdue today!");
        } else {
            for (Rental rental : overdueRentals) {
                String message = generateOverdueRentalMessage(rental);
                sentMessage(message);
            }
        }
    }

    private String generateOverdueRentalMessage(Rental rental) {
        // Генерація повідомлення про прострочену оренду на основі даних оренди
        // Поверніть сформоване повідомлення як рядок
    }

    private String getChatIdForCurrentUser() {
        // Отримати chat ID для поточного користувача, наприклад, на основі авторизованого користувача або збереженого в сесії
        String currentUserChatId = // отримати chat ID для поточного користувача
                User user = userService.getUserByChatId(currentUserChatId);
        return user.getChatId();
    }
}
