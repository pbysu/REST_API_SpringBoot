package me.bysu.restAPI.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {
    public void validate(EventDto eventDto, Errors errors){

        if(eventDto.getMaxPrice() < eventDto.getBasePrice() &&eventDto.getMaxPrice()!=0){
            errors.rejectValue("basePrice", "wrongValue","BasePriceIsWrong");
            errors.rejectValue("basePrice", "wrongValue","MaxPriceIsWrong");
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        if(endEventDateTime.isBefore(eventDto.getEndEventDateTime()) || endEventDateTime.isBefore((eventDto.getCloseEnrollmentDateTime()))
        || endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())){
            errors.rejectValue("endEventDateTime","wrongValue", "endEventTime is wrong");
        }
        // skip beginEventDateTime
        // closeEnrollmentDateTime
    }
}
