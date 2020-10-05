package me.backendj.currencyconverter.currency;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.backendj.currencyconverter.currency.dto.RequestDto;

@Getter
public class AmountReceived {
    private Double amountReceived;

    public AmountReceived(RequestDto currency) {
        this.amountReceived = convert(currency);
    }

    private Double convert(RequestDto currency) {
        return currency.getExchangeRate() * currency.getRemittance();
    }
}
