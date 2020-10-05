# Currency Converter
---
currencylayer.com 의 환율 정보를 사용한 환율 계산기
> 스프링 기술을 최대한 활용하여 구현


1. 사용 기술
> SpringBoot, Java11, Gradle, Spring Data JPA, H2 Database, Spring Validation, JQuery, JQuery Numbers
  
2. 구현 시나리오
- 환율 정보는 웹 애플리케이션 구동 시 1회 조회하여 Database에 저장한다.
- 이 후 스케쥴러를 통해 1시간에 한 번씩 환율 정보를 업데이트 한다.
- 수취 국가 선택 시 환율 정보는 DB에서 조회한다.
- 송금액은 서버에서 계산하여 출력한다.
- API 요청 정보는 설정 파일으로 관리한다.

3. 테스트
- 핵심 로직(수취 금액 변환) 단위 테스트
- MockMvc를 통한 통합테스트
