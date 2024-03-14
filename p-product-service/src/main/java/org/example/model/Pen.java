package org.example.model;

import lombok.Data;

import java.util.List;

@Data
public class Pen {

    private String color;

    private List<Point> points;
}
