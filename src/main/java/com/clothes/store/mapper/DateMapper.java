package com.clothes.store.mapper;

import com.clothes.store.Constants;
import org.mapstruct.Mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper
public interface DateMapper {
    public default String asString(Date date) {
        return date != null ? new SimpleDateFormat(Constants.DATE_FORMAT)
                .format( date ) : null;
    }
    public default Date asDate(String date) {
        try {
            return date != null ? new SimpleDateFormat(Constants.DATE_FORMAT)
                    .parse( date ) : null;
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
    }
}
