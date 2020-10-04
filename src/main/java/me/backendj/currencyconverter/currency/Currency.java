package me.backendj.currencyconverter.currency;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Currency {
    @Id
    @GeneratedValue
    private Integer id;
    private String source;
    private String receivingCountry;
    private String quotes;
    private Double exchangeRate;
    private LocalDateTime timestamp;

    @Builder
    public Currency(String source, String receivingCountry, String quotes, Double exchangeRate, LocalDateTime timestamp) {
        this.source = source;                       //USD
        this.receivingCountry = receivingCountry;   //KRW
        this.quotes = quotes;                       //USDKRW
        this.exchangeRate = exchangeRate;           //1156.12
        this.timestamp = timestamp;
    }
}
