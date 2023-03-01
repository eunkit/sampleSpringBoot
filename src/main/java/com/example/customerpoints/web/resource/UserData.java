package com.example.customerpoints.web.resource;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data //Defining the class/object as data class using lombok
@NoArgsConstructor
public class UserData {

    @NotNull
    private UUID customerID;

    @Digits(integer = 8, fraction = 2)
    @PositiveOrZero
    private BigDecimal transactionAmount;

    @Past
    private LocalDate date;

}
