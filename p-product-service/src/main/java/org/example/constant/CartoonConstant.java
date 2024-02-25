package org.example.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartoonConstant {

    public static final List<String> ORDER_FIELD = new ArrayList<>(Arrays.asList(
            "salesNum","price","lastUpdateTime","createTime"
    ));


    public static final String STATUS_DOING = "doing";

    public static final String STATUS_FINISHED = "finished";

    public static final String DEFAULT_COVER = "http://127.0.0.1:9000/cartoon/iACSAzghT2.png";
}
