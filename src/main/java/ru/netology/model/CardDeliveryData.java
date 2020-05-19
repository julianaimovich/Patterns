package ru.netology.model;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CardDeliveryData {
    private final String city;
    private final String date;
    private final String fullName;
    private final String phoneNumber;
}