package me.backendj.currencyconverter.currency.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.backendj.currencyconverter.currency.Currency;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto {
    private String source;
    private String quotes;
    private String receivingCountry;
    private Double exchangeRate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime timestamp;

    public static ResponseDto toDto(Currency currency) {
        return ResponseDto.builder()
                .source(currency.getSource())
                .quotes(currency.getQuotes())
                .receivingCountry(currency.getReceivingCountry())
                .exchangeRate(currency.getExchangeRate())
                .timestamp(currency.getTimestamp())
                .build();
    }
}
