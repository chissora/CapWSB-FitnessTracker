package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingMapper {
    private final UserRepository userRepository;

    public TrainingDto toDto(Training training) {
        UserDto userDto = null;
        if (training.getUser() != null) {
            userDto = new UserDto(
                    training.getUser().getId(),
                    training.getUser().getFirstName(),
                    training.getUser().getLastName(),
                    training.getUser().getBirthdate(),
                    training.getUser().getEmail()
            );
        }

        return new TrainingDto(
                training.getId(),
                userDto,
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }

    public Training toEntity(TrainingDto trainingDto) {
        User user = userRepository.findById(trainingDto.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        return new Training(
                user,
                trainingDto.getStartTime(),
                trainingDto.getEndTime(),
                trainingDto.getActivityType(),
                trainingDto.getDistance(),
                trainingDto.getAverageSpeed()
        );
    }
}
