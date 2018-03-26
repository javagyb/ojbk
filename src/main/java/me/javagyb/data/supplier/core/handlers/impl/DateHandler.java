package me.javagyb.data.supplier.core.handlers.impl;


import lombok.EqualsAndHashCode;
import me.javagyb.data.supplier.annotations.DS_Date;
import me.javagyb.data.supplier.core.handlers.AnnotationHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by javagyb on 2018/1/30.
 */
@EqualsAndHashCode
public class DateHandler extends AnnotationHandler<DS_Date,Date> {

    @Override
    public Date value(DS_Date annotation) {
        String format = annotation.format();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(annotation.value());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
