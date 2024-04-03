package ua.dokat.utils.webflux;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ua.dokat.entity.json.Entity;
import ua.dokat.entity.json.http.HttpRequestInfo;
import ua.dokat.entity.json.http.TraderInformation;
import ua.dokat.exeptions.entity.TestException;

@Component
@Log4j
public class WebClientUtils {

    private static final String REQUEST_IS_MADE = "The request was made. URL: %s";
    private static final String QUERY_ERROR = "Error when making a request to %s \n Most likely status 429 or 500. %s";
    private static final String UNEXPECTED_QUERY_ERROR = "Unexpected query error: %s \nStackTrace: %s";
    private static final String UNEXPECTED_QUERY_ERROR_MESSAGE = "Unexpected query error";

    //todo: заменить TestException во всех методах.
    //todo: чекнуть видос про отлов ексепшенов. это чтобы уменьшить код.
    //todo: message в конструкторе Entity должен прокидываться (НАВРНОЕ)
    public WebClient.ResponseSpec sendRequest(WebClient.RequestHeadersUriSpec<?> requestSpec, HttpRequestInfo requestInfo) throws TestException {

        try {

            log.debug(logMessage(REQUEST_IS_MADE, requestInfo));

            return requestSpec
                    .uri(requestInfo.params())
                    .retrieve();

        }catch (WebClientResponseException e){
            log.error(logMessage(QUERY_ERROR, requestInfo, e));
            throw new TestException();
        }catch (Exception e){
            log.error(logMessage(UNEXPECTED_QUERY_ERROR, requestInfo, e));
            throw new TestException();
        }
    }

    public WebClient.ResponseSpec sendRequest(WebClient.RequestHeadersUriSpec<?> requestSpec, HttpRequestInfo requestInfo, TraderInformation information) throws TestException {

        try {

            log.debug(logMessage(REQUEST_IS_MADE, requestInfo));

            return requestSpec
                    .uri(requestInfo.params())
                    .headers(httpHeaders -> httpHeaders.putAll(headers(information)))
                    .retrieve();

        }catch (WebClientResponseException e){
            log.error(logMessage(QUERY_ERROR, requestInfo, e));
            throw new TestException();
        }catch (Exception e){
            log.error(logMessage(UNEXPECTED_QUERY_ERROR, requestInfo, e));
            throw new TestException();
        }
    }

    public ResponseEntity<Void> sendSyncRequest(WebClient.RequestHeadersUriSpec<?> requestSpec, HttpRequestInfo requestInfo) throws TestException {

        try {

            log.debug(logMessage(REQUEST_IS_MADE, requestInfo));

            return requestSpec
                    .uri(requestInfo.params())
                    .retrieve()
                    .toBodilessEntity()
                    .block();

        }catch (WebClientResponseException e){
            log.error(logMessage(QUERY_ERROR, requestInfo, e));
            throw new TestException();
        }catch (Exception e){
            log.error(logMessage(UNEXPECTED_QUERY_ERROR, requestInfo, e));
            throw e;
        }
    }

    public ResponseEntity<Void> sendSyncRequest(WebClient.RequestHeadersUriSpec<?> requestSpec, HttpRequestInfo requestInfo, TraderInformation information) throws TestException {

        try {

            log.debug(logMessage(REQUEST_IS_MADE, requestInfo));

            return requestSpec
                    .uri(requestInfo.params())
                    .headers(httpHeaders -> httpHeaders.putAll(headers(information)))
                    .retrieve()
                    .toBodilessEntity()
                    .block();

        }catch (WebClientResponseException e){
            log.error(logMessage(QUERY_ERROR, requestInfo, e));
            throw new TestException();
        }catch (Exception e){
            log.error(logMessage(UNEXPECTED_QUERY_ERROR, requestInfo, e));
            throw new TestException();
        }

    }

    public <T> Entity<T> sendPostAndGetResponse(HttpRequestInfo requestInfo, Class<? extends T> obj) {

        try {

            WebClient client = WebClient.create(requestInfo.url());
            T entity = sendRequest(client.post(), requestInfo).bodyToMono(obj).block();

            return fastBuild(entity, HttpStatus.OK);

        } catch (TestException e) {
            return Entity.<T>builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(UNEXPECTED_QUERY_ERROR_MESSAGE)
                    .build();
        }
    }

    public <T> Entity<T> sendPostAndGetResponse(HttpRequestInfo requestInfo, TraderInformation information, Class<? extends T> obj){

        try {

            WebClient client = WebClient.create(requestInfo.url());
            T entity = sendRequest(client.post(), requestInfo, information).bodyToMono(obj).block();

            return fastBuild(entity, HttpStatus.OK);

        }catch (TestException e){
            return Entity.<T>builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(UNEXPECTED_QUERY_ERROR_MESSAGE)
                    .build();
        }
    }

    public <T> Entity<T> sendGetAndGetResponse(HttpRequestInfo requestInfo, Class<? extends T> obj){

        try {

            WebClient client = WebClient.create(requestInfo.url());
            T entity = sendRequest(client.get(), requestInfo).bodyToMono(obj).block();

            return fastBuild(entity, HttpStatus.OK);

        }catch (TestException e){
            return Entity.<T>builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(UNEXPECTED_QUERY_ERROR_MESSAGE)
                    .build();
        }
    }

    public <T> Entity<T> sendGetAndGetResponse(HttpRequestInfo requestInfo, TraderInformation information, Class<? extends T> obj){

        try {

            WebClient client = WebClient.create(requestInfo.url());
            T entity = sendRequest(client.get(), requestInfo, information).bodyToMono(obj).block();

            return fastBuild(entity, HttpStatus.OK);

        }catch (TestException e){
            return Entity.<T>builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(UNEXPECTED_QUERY_ERROR_MESSAGE)
                    .build();
        }
    }

    private <T> Entity<T> fastBuild(T entityObject, HttpStatus status){
        return Entity.<T>builder()
                .entityObject(entityObject)
                .status(status)
                .build();
    }

    private HttpHeaders headers(TraderInformation information){
        HttpHeaders headers = new HttpHeaders();
        headers.add("cookie", information.cookie());
        headers.add("X-CSRFToken", information.token());
        return headers;
    }

    private String logMessage(String message, HttpRequestInfo info){
        return String.format(message, info.getAbsoluteUrl());
    }

    private String logMessage(String message, HttpRequestInfo info, Exception e){
        return String.format(message, info.getAbsoluteUrl(), e);
    }
}