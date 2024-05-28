package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Interface (API) for modifying operations on {@link Training} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface TrainingService {

    Training createTraining(Training training);

    Optional<Training> getTraining(long trainingId);

    List<Training> getAllTrainingsForDedicatedUser(long userId);

    List<Training> findAllFinishedTrainings();

    List<Training> findAllFinishedTrainingsBefore(LocalDate date);

    List<Training> findALlTrainingsByActivity(ActivityType activityType);

    Training updateTraining(Training training);
}