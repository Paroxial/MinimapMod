package com.paroxial.minimap.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Minimap {
    private final int cornerDistance = 10;
    private final float radius = 40;
    private float markerSize = 5;
    private boolean rotating = true;
}
