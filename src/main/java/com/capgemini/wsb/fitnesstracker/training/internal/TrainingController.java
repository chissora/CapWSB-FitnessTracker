package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingController {

    @Lazy
    private final TrainingServiceImpl trainingService;

    private final TrainingMapper trainingMapper;

    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.getAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/ofUser/{userId}")
    public List<TrainingDto> getTrainingsByUserId(@PathVariable long userId) {
        return trainingService.getAllTrainingsForDedicatedUser(userId).stream().map(trainingMapper::toDto).toList();
    }

    @PostMapping
    public Training addTraining(@RequestBody TrainingDto trainingDto) {
        return trainingService.createTraining(trainingMapper.toEntity(trainingDto));
    }

    @GetMapping("/finishedTrainings")
    public List<TrainingDto> getFinishedTrainings() {
        return trainingService.findAllFinishedTrainings().stream().map(trainingMapper::toDto).toList();
    }

    @GetMapping("/finishedTrainings/afterDate/{date}")
    public List<TrainingDto> getFinishedTrainingsBefore(@PathVariable LocalDate date) {
        return trainingService.findAllFinishedTrainingsBefore(date).stream().map(trainingMapper::toDto).toList();
    }

    @GetMapping("/byActivity/{activity}")
    public List<TrainingDto> getTrainingsByActivity(@PathVariable ActivityType activity) {
        return trainingService.findALlTrainingsByActivity(activity).stream().map(trainingMapper::toDto).toList();
    }

    @PatchMapping("/update/training/byAverageSpeed/{id}")
    public void updateTraining(@PathVariable Long id, @RequestParam double averageSpeed ) {
        Optional<Training> optionalTraining = trainingService.getTraining(id);
        if (optionalTraining.isPresent()) {
            Training training = optionalTraining.get();
            training.setAverageSpeed(averageSpeed);
            trainingService.updateTraining(training);
        }
        else { throw new TrainingNotFoundException(id); }
    }

}