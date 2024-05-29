package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService trainingService;
    private final TrainingMapper trainingMapper;

    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAllTrainings() {
        List<TrainingDto> trainings = trainingService.getAllTrainings().stream()
                .map(trainingMapper::toDto)
                .toList();
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingDto> getTrainingById(@PathVariable Long id) {
        return trainingService.getTraining(id)
                .map(trainingMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new TrainingNotFoundException(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TrainingDto>> getAllTrainingsForDedicatedUser(@PathVariable Long userId) {
        List<TrainingDto> trainings = trainingService.getAllTrainingsForDedicatedUser(userId).stream()
                .map(trainingMapper::toDto)
                .toList();
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/finished/{afterTime}")
    public ResponseEntity<List<TrainingDto>> getAllFinishedTrainingsBefore(@PathVariable String afterTime) {
        List<TrainingDto> trainings = trainingService.getAllFinishedTrainingsBefore(afterTime).stream()
                .map(trainingMapper::toDto)
                .toList();
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/activityType")
    public ResponseEntity<List<TrainingDto>> getAllTrainingByActivityType(@RequestParam ActivityType activityType) {
        List<TrainingDto> trainings = trainingService.getAllTrainingByActivityType(activityType).stream()
                .map(trainingMapper::toDto)
                .toList();
        return ResponseEntity.ok(trainings);
    }

    @PostMapping
    public ResponseEntity<Training> createTraining(@RequestBody TrainingDto trainingDto) {
        Training training = trainingMapper.toEntity(trainingDto);
        Training createdTraining = trainingService.createTraining(training);
        return ResponseEntity.status(201).body(createdTraining);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Training> updateTraining(@PathVariable Long id, @RequestBody TrainingDto trainingDto) {
        Training training = trainingMapper.toEntity(trainingDto);
        Training updatedTraining = trainingService.updateTraining(id, training);
        return ResponseEntity.ok(updatedTraining);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return ResponseEntity.noContent().build();
    }
}
