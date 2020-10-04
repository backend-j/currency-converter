# Currency Converter
---
currencylayer.com 의 환율 정보를 사용한 환율 계산기


1. 사용 기술
> SpringBoot, Java11, Spring Data JPA, H2 Database, Spring Validation, JQuery, JQuery Numbers
  
2. 구현 시나리오
- 환율 정보는 웹 애플리케이션 구동 시 1회 조회하여 Database에 저장한다.
- 이 후 스케쥴러를 통해 1시간에 한 번씩 환율 정보를 업데이트 한다.
- 수취 국가 선택 시 환율 정보는 서버에서 조회한다.
- 송금액은 서버에서 계산한 값을 출력한다
- 수취 금액 Validation은 Spring Validation을 이용한다.
- API 요청 정보는 설정 파일으로 관리한다.

