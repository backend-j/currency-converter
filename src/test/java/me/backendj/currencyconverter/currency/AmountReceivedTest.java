package me.backendj.currencyconverter.currency;

import me.backendj.currencyconverter.currency.dto.RequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class AmountReceivedTest {
    @Test
    @DisplayName("생성 테스트")
    void create() {
        double exchangeRate = 10.0;
        double remittance = 100.0;
        RequestDto currency = getRequestCurrency(exchangeRate, remittance);

        assertThatCode(() -> {
            new AmountReceived(currency);
        }).doesNotThrowAnyException();
    }

    private RequestDto getRequestCurrency(double exchangeRate, double remittance) {
        RequestDto currency = RequestDto.builder()
                .receivingCountry("KRW")
                .exchangeRate(exchangeRate)
                .remittance(remittance)
                .build();
        return currency;
    }


    @Test
    @DisplayName("수취금액 계산 테스트")
    void convert() {
        //given
        double exchangeRate = 10.0;
        double remittance = 100.0;
        RequestDto currency = getRequestCurrency(exchangeRate, remittance);

        //when
        AmountReceived amountReceived = new AmountReceived(currency);

        //then
        assertThat(amountReceived.getAmountReceived()).isEqualTo(remittance * exchangeRate);

    }


}
