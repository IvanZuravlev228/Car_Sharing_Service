package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.NotificationService;
import com.example.carsharingservice.service.UserService;
import com.example.carsharingservice.telegrambot.NotificationBot;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class TelegramNotificationServiceImpl implements NotificationService {
    private final NotificationBot notificationBot;
    private final UserService userService;

    @Override
    public void sendMessageAboutSuccessRent(Rental rental) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(rental.getUser().getChatId());
        sendMessage.setText(messageAboutSuccessRent(rental));
        try {
            notificationBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can't sent message to Chat bot");
        }
    }

    @Scheduled(cron = "0 0 12 * * ?")
    @Override
    public void checkOverdueRentals() {
        LocalDate localDate = LocalDate.now();
        /*
        List<Rental> overdueRent = rentalService.findByOverdueRent(localDate);
        for (Rental rental : overdueRent) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(rental.getUser().getChatId());
            sendMessage.setText(messageAboutOverdueRent(rental, localDate));
        }
        */
    }

    @Override
    public void sendMessageToAdministrators(String message) {
        List<User> managers = userService.findUserByRole(User.Role.MANAGER);
        for (User user : managers) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText(message);
            try {
                notificationBot.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException("Message: " + message + " isn't sent to admins chat");
            }
        }
    }

    private String messageAboutSuccessRent(Rental rental) {
        String text = rental.getUser().getFirstName() + ", you are successfully rent: "
                + rental.getCar().getModel() + " at " + rental.getRentalStart().toString()
                + ". Please you should return car until " + rental.getRentalReturn().toString()
                + ". Your daily fee: " + rental.getCar().getDailyFee().toString();
        return text;
    }

    private String messageAboutOverdueRent(Rental rental, LocalDate date) {
        String text = "You overdue your rental payment at " + date.toString()
                + ". Please, pay your fine!";
        return text;
    }
}
