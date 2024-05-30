package com.capgemini.wsb.fitnesstracker.notification;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Profile("email")
@EnableScheduling
@Component
@AllArgsConstructor
@Slf4j
public class RaportGenerator {

    private final TrainingService trainingService;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 20 * * 0")
    public void weeklyReport() {
        Date lastWeek = Date.from(LocalDate.now().minusWeeks(1).atStartOfDay().toInstant(ZoneOffset.UTC));
        log.info("Last week: {}", lastWeek);

        List<Training> trainings = trainingService.getTrainingsEndingAfter(lastWeek);

        log.info("Number of trainings: {}", trainings.size());

        Map<User, List<Training>> trainingsByUser = trainings.stream()
                .collect(Collectors.groupingBy(Training::getUser));

        trainingsByUser.forEach((user, _trainings) -> {
            log.info("-------------------");
            log.info("User: {}", user);
            log.info("Trainings: {}", _trainings);

            StringBuilder body = new StringBuilder("Training report");
            for (Training training : _trainings) {
                body.append("\nActivity: ").append(training.getActivityType());
                body.append("\nStart: ").append(training.getStartTime());
                body.append("\nEnd: ").append(training.getEndTime());
                body.append("\nDistance: ").append(training.getDistance());
                body.append("\nAverage speed: ").append(training.getAverageSpeed());
                body.append("\n\n");
            }
            body.append("\nTrainings last week: ").append(_trainings.size());
            body.append("\nTotal trainings: ").append(trainingService.getTrainingsByUser(user.getId()).size());

            log.info("Email: {}", user.getEmail());
            log.info("Body: {}", body.toString());

            log.info("Sending email");
            log.info("-------------------");

            emailService.sendEmail(user.getEmail(), "Training report", body.toString());
        });

        log.info("Report processing");
    }
}
