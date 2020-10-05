package me.backendj.currencyconverter.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.backendj.currencyconverter.currency.dto.RequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CurrencyRepository currencyRepository;

    @Test
    @DisplayName("수취 국가별 환율 정보 조회")
    void findByCurrency() throws Exception {
        String receivingCountry = "KRW";


        Currency currency = currencyRepository.findByReceivingCountry(receivingCountry).get();

        mockMvc.perform(get("/currency")
                .param("receivingCountry", receivingCountry))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("receivingCountry").value(receivingCountry))
                .andExpect(jsonPath("exchangeRate").value(currency.getExchangeRate()));
    }

    @Test
    @DisplayName("수취 금액 변환 요청")
    void convert() throws Exception {
        double exchangeRate = 10.0;
        double remittance = 100.0;
        RequestDto currency = getRequestCurrency(exchangeRate, remittance);

        mockMvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(currency)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("amountReceived").exists());
    }

    @Test
    @DisplayName("잘못된 송금액 요청")
    void convert_400() throws Exception {
        double exchangeRate = 10.0;
        double remittance = -10.0;
        RequestDto currency = getRequestCurrency(exchangeRate, remittance);

        mockMvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(currency)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("[0].field").value("remittance"))
                .andExpect(jsonPath("[0].code").value("wrongValue"))
        ;
    }


    private RequestDto getRequestCurrency(double exchangeRate, double remittance) {
        RequestDto currency = RequestDto.builder()
                .receivingCountry("KRW")
                .exchangeRate(exchangeRate)
                .remittance(remittance)
                .build();
        return currency;
    }
}
