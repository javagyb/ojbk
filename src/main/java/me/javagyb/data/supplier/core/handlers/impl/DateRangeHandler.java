package me.javagyb.data.supplier.core.handlers.impl;

import lombok.EqualsAndHashCode;
import me.javagyb.data.supplier.annotations.DS_DateRange;
import me.javagyb.data.supplier.core.handlers.AnnotationHandler;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by javagyb on 2018/1/30.
 */
@EqualsAndHashCode
public class DateRangeHandler extends AnnotationHandler<DS_DateRange, Date> {

    ThreadLocal<Date> current = new ThreadLocal<>();
    ThreadLocal<Date> end = new ThreadLocal<>();

    @Override
    public Date value(DS_DateRange annotation) {

        String format = annotation.formate();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date start=null,endDate = null;
        if (current.get() == null) {
            try {
                 start = sdf.parse(annotation.start());
                current.set(start);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (end.get() == null) {
            try {
                 endDate = sdf.parse(annotation.end());
                end.set(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        // date validate
        if(start.compareTo(endDate)<=0){
            throw new IllegalArgumentException(" start must before end");
        }


        // loop
        if(current.get().compareTo(end.get())>=0){
            current.set(start);
        }

        Date date = current.get();
         if(annotation.step()== DS_DateRange.Step.hour){
             Date addDays = DateUtils.addDays(date, 1);
             current.set(addDays);
             return addDays;
         }

        if(annotation.step()== DS_DateRange.Step.second){
            Date addDays = DateUtils.addSeconds(date, 1);
            current.set(addDays);
            return addDays;
        }

        if(annotation.step()== DS_DateRange.Step.minute){
            Date addDays = DateUtils.addMinutes(date, 1);
            current.set(addDays);
            return addDays;
        }

        if(annotation.step()== DS_DateRange.Step.week){
            Date addDays = DateUtils.addWeeks(date, 1);
            current.set(addDays);
            return addDays;
        }

        if(annotation.step()== DS_DateRange.Step.month){
            Date addDays = DateUtils.addMonths(date, 1);
            current.set(addDays);
            return addDays;
        }

        return null;
    }
}
