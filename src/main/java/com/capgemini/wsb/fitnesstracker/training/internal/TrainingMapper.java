package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class TrainingMapper {

    private final UserMapper userMapper;
    TrainingDto toDto(Training training) {

        return new TrainingDto(training.getUser(),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getAverageSpeed(),
                training.getDistance());
    }

    Training toEntity(TrainingDto trainingDto) {
        return new Training(
                trainingDto.getUser(),
                trainingDto.getStartTime(),
                trainingDto.getEndTime(),
                trainingDto.getActivityType(),
                trainingDto.getDistance(),
                trainingDto.getAverageSpeed());
    }

}