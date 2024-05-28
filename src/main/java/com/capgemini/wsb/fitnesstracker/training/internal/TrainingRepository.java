package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Query searching all trainings for given user ID
     *
     * @param userID ID of a user, whom trainings are to be searched
     * @return {@link List} containing found trainings
     */
    @Query("select training from Training training where training.user.id = :userID")
    List<Training> findAllTrainingsByUserID(@Param("userID") long userID);

    /**
     * Query searching all finished trainings
     *
     * @param today present date
     * @return {@link List} containing found trainings
     */
    @Query("select training from Training training where training.endTime < :today")
    List<Training> findAllFinishedTrainings(@Param("today") LocalDate today);

    /**
     * Query searching all finished trainings before given date
     *
     * @param date given date
     * @return {@link List} containing found trainings
     */
    @Query("select training from Training training where training.endTime < :date")
    List<Training> findAllFinishedTrainingsBefore(@Param("date") LocalDate date);

    /**
     * Query searching all trainings by activity type
     *
     * @param activityType Type of activity
     * @return {@link List} containing found trainings
     */
    @Query("select training from Training training where training.activityType = :activityType")
    List<Training> findAllTrainingsByActivity(@Param("activityType") ActivityType activityType);

}