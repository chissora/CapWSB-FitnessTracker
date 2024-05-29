package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.List;
import java.util.Optional;

public interface TrainingService {
    List<Training> getAllTrainings();
    List<Training> getAllTrainingsForDedicatedUser(Long userId);
    List<Training> getAllFinishedTrainingsBefore(String afterTime);
    List<Training> getAllTrainingByActivityType(ActivityType activityType);
    Training createTraining(Training training);
    Training updateTraining(Long trainingId, Training training);
    Optional<Training> getTraining(long trainingId);
    void deleteTraining(Long trainingId);
}
