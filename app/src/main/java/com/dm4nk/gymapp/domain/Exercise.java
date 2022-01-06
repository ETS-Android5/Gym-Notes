package com.dm4nk.gymapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Exercise {
    private Integer id;
    private String name;
    private Integer sets;
    private Integer reps;
    private String weight;
    private Long date;
    private String url;
}
