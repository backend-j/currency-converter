package me.backendj.currencyconverter.currency;

import javassist.NotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.backendj.currencyconverter.currency.dto.RequestDto;
import me.backendj.currencyconverter.currency.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@Controller
public class CurrencyController {

    private final CurrencyRepository repository;
    private final CurrencyValidator currencyValidator;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/currency")
    @ResponseBody
    public ResponseDto findByCurrency(@RequestParam String receivingCountry) throws NotFoundException {
        Currency currency = repository.findByReceivingCountry(receivingCountry)
                .orElseThrow(() -> new NotFoundException(receivingCountry));
        return ResponseDto.toDto(currency);
    }

    @PostMapping("/convert")
    @ResponseBody
    public ResponseEntity convert(@RequestBody @Valid RequestDto currency, Errors errors) {
        currencyValidator.validate(currency, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        AmountReceived result = new AmountReceived(currency);
        return ResponseEntity.ok().body(result);
    }

    @Data
    @NoArgsConstructor
    static class AmountReceived {
        private Double amountReceived;

        public AmountReceived(RequestDto currency) {
            this.amountReceived = convert(currency);
        }

        private Double convert(RequestDto currency) {
            return currency.getExchangeRate() * currency.getRemittance();
        }
    }
}
