package com.example.backend_webshop.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductBehavior {
    private Integer clickCount;
    private Date lastClicked;

    public void updateClickCount(){
        clickCount++;
        setLastClicked(new Date());
    }
}
