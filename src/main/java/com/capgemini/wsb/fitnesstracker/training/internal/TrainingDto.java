package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.Data;

import java.util.Date;

@Data
public class TrainingDto {

    private Long id;

    private User user;

    private Date startTime;

    private Date endTime;

    private ActivityType activityType;

    private double distance;

    private double averageSpeed;

    public TrainingDto(
            final User user,
            final Date startTime,
            final Date endTime,
            final ActivityType activityType,
            final double distance,
            final double averageSpeed) {
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }

}