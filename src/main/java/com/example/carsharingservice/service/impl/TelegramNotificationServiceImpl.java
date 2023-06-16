package com.example.carsharingservice.service.impl;

import static java.time.temporal.ChronoUnit.DAYS;

import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.NotificationService;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.service.UserService;
import com.example.carsharingservice.telegrambot.NotificationBot;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class TelegramNotificationServiceImpl implements NotificationService {
    private final NotificationBot notificationBot;
    private UserService userService;
    private RentalService rentalService;

    @Lazy
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Lazy
    @Autowired
    public void setRentalService(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Scheduled(cron = "0 * * * * ?")
    @Override
    public void checkOverdueRentals() {
        LocalDate today = LocalDate.now();
        List<Rental> overdueRent = rentalService.findByOverdueRent(today);
        for (Rental rental : overdueRent) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(rental.getUser().getChatId());
            sendMessage.setText(messageAboutOverdueRent(rental, today,
                    rental.getRentalStart(), rental.getRentalReturn()));
            try {
                notificationBot.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException("Message about overdue doesn't sent");
            }
        }
    }

    @Override
    public void sendMessageAboutSuccessRent(Rental rental) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(rental.getUser().getChatId());
        sendMessage.setText(getMessageAboutSuccessRent(rental));
        try {
            notificationBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can't sent message to Chat bot");
        }
    }

//    @Override
//    public void sendMessageToAdministrators(String message) {
//        List<User> managers = userService.findUserByRole(User.Role.MANAGER);
//        for (User user : managers) {
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setChatId(user.getChatId());
//            sendMessage.setText(message);
//            try {
//                notificationBot.execute(sendMessage);
//            } catch (TelegramApiException e) {
//                throw new RuntimeException("Message: " + message + " isn't sent to admins chat");
//            }
//        }
//    }

    public void sendMessageToAllUsers(String message) {
        List<User> users = userService.findUserByRole(User.Role.CUSTOMER);
        for (User user : users) {
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

    @Override
    public void sendMessageAboutSuccessfulPayment(Long rentalId, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(getMessageAboutSuccessfulPayment(rentalId));
        try {
            notificationBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can't sent message to Chat bot");
        }
    }

    @Override
    public void sendMessageWithPaymentUrl(String url, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(getMessageAboutPaymentUrl(url));
        try {
            notificationBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can't sent message to Chat bot");
        }
    }

    private String getMessageAboutSuccessfulPayment(Long rentalId) {
        return "You have successfully paid for the car rental."
                + " Rental ID number " + rentalId + ". For details, contact the administrator";
    }

    private String getMessageAboutPaymentUrl(String url) {
        return "You can pay your rent through this link: " + url;
    }

    private String getThankYouMessage() {
        return "Thank you for using our car sharing service\nSee you later!";
    }

    private String getMessageAboutSuccessRent(Rental rental) {
        LocalDate rentalStart = rental.getRentalStart();
        LocalDate rentalReturn = rental.getRentalReturn();
        BigDecimal dailyFee = rental.getCar().getDailyFee();
        return rental.getUser().getFirstName() + ", you are successfully rent:\n"
                + rental.getCar().getModel() + " from " + rentalStart.toString()
                + " to " + rentalReturn.toString() + "\n"
                + "Rental price per day: " + dailyFee.toString() + "$\n"
                + "Total price: " + getTotalPrice(rentalStart, rentalReturn, dailyFee) + "$";
    }

    private String getTotalPrice(LocalDate start, LocalDate end, BigDecimal pricePerDay) {
        return pricePerDay.multiply(new BigDecimal(DAYS.between(start, end))).toString();
    }

    private String messageAboutOverdueRent(Rental rental, LocalDate today,
                                           LocalDate start, LocalDate end) {
        BigDecimal dailyFee = rental.getCar().getDailyFee();
        return "You rented a car on " + start.toString() + " and had to return it on " + end
                + "\nThe cost of rent was " + getTotalPrice(start, end, dailyFee) + "$"
                + ", but now every day the cost will increase "
                + "by the cost of rent per day. Now you have to pay: "
                + getTotalPrice(start, today, dailyFee) + "$";
    }
}
