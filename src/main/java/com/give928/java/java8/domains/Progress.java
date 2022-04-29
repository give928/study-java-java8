package com.give928.java.java8.domains;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
public class Progress {
    private Duration studyDuration;
    private boolean finished;
}
