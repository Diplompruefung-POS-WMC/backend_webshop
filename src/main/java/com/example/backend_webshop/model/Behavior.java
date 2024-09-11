package com.example.backend_webshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Behavior {
    private Integer clickCount;
    private Date date;

    public void updateClickCount() {
        this.clickCount++;
        setDate(new Date());
    }
}

