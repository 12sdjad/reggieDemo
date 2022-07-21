package com.cmplete.reggiedemo.dto;

import com.cmplete.reggiedemo.entity.Setmeal;
import com.cmplete.reggiedemo.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;

}
