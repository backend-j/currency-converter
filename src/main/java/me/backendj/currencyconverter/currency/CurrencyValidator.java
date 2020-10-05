package me.backendj.currencyconverter.currency;

import me.backendj.currencyconverter.currency.dto.RequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CurrencyValidator {

    public void validate(RequestDto form, Errors errors) {
        Double remittance = form.getRemittance();
        if (null == remittance || isNotInRange(remittance) || isNotNumber(remittance)) {
            errors.rejectValue("remittance", "wrongValue", "송금액이 바르지 않습니다 (송금은 0 ~ 10,000까지 가능)");
        }
    }

    private boolean isNotInRange(Double remittance) {
        return remittance < 0 || remittance > 10000;
    }

    private boolean isNotNumber(Double remittance) {
        return false;
    }
}
