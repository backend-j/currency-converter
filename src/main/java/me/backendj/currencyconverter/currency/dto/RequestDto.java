package me.backendj.currencyconverter.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestDto {

    @NotEmpty(message = "수취국가를 선택하세요")
    private String receivingCountry;

    @NotNull(message = "환율 정보가 없습니다")
    private Double exchangeRate;

    private Double remittance;
}
