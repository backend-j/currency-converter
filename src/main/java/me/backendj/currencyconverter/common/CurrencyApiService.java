package me.backendj.currencyconverter.common;

import me.backendj.currencyconverter.currency.Currency;
import me.backendj.currencyconverter.currency.CurrencyRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@Service
public class CurrencyApiService {

    private final RestTemplate restTemplate;
    private final CurrencyRepository currencyRepository;

    @Value("${currency.api.url}")
    private String currencyApiUrl;
    @Value("${currency.api.access-key}")
    private String accessKey;
    @Value("${currency.api.currencies}")
    private String currencies;

    public CurrencyApiService(RestTemplateBuilder restTemplate, CurrencyRepository currencyRepository) {
        this.restTemplate = restTemplate.build();
        this.currencyRepository = currencyRepository;
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    public void getCurrency() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(currencyApiUrl)
                .queryParam("access_key", accessKey)
                .queryParam("currencies", currencies);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        CurrencyApiResponse CurrencyAPIResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, CurrencyApiResponse.class).getBody();
        //1시간에 한 번씩 환율 정보 업데이트
        currencyRepository.deleteAll();
        CurrencyAPIResponse.getQuotes().entrySet().stream()
                .forEach(e -> {
                    Currency currency = Currency.builder()
                            .source("USD")
                            .quotes(e.getKey())
                            .receivingCountry(e.getKey().substring(3))
                            .exchangeRate(e.getValue())
                            .timestamp(LocalDateTime.now())
                            .build();
                    currencyRepository.save(currency);
                });
    }
}
