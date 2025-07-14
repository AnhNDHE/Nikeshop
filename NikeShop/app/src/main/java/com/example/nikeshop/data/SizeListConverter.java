package com.example.nikeshop.data;

import androidx.room.TypeConverter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SizeListConverter {
    @TypeConverter
    public static String fromSizeList(List<String> sizes) {
        if (sizes == null || sizes.isEmpty()) return "";
        return String.join(",", sizes);
    }

    @TypeConverter
    public static List<String> toSizeList(String data) {
        if (data == null || data.isEmpty()) return null;
        return Arrays.stream(data.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
