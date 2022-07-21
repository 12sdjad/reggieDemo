package com.cmplete.reggiedemo.dto;

import com.cmplete.reggiedemo.entity.Dish;
import com.cmplete.reggiedemo.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
