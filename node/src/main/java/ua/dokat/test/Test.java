package ua.dokat.test;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import ua.dokat.entity.json.Goods;
import ua.dokat.test.entity.Item;
import ua.dokat.test.entity.Items;

import java.util.Collections;
import java.util.List;

public class Test {

    private static final String API_URL = "https://api.buff.market/api";
    private static final String API_ARGUMENTS = "/market/goods/info?game=csgo&goods_id=28389";

    public static void main(String[] args) {
        getItem();
    }

    public static void getItem(){
        WebClient webClient = WebClient.create(API_URL);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "Device-Id=r3xCiios2TsoXtQKrjat; forterToken=0eea571560ff48d9b781071530204eef_1707242263911__UDF43-m4_13ck_; ftr_ncd=6; fblo_881005522527906=y; session=1-aZUzFrnCkGhzAajVbaoI1Z6Q8YhRtjXkAuPv6QjTTuo82045652511; Locale-Supported=ru; csrf_token=IjY0Nzc3OGM2YWRmZTI3ZmRiMTZmYjlmMDQ5OTNlYWQwZmZlMzBiZGQi.GKQGYw.YGNST0F_QHEW3qD8Mh8mlX1Mi_g");
        headers.set("X-CSRFToken", "IjY0Nzc3OGM2YWRmZTI3ZmRiMTZmYjlmMDQ5OTNlYWQwZmZlMzBiZGQi.GKDjYg.3AazTNABGiUOjNrzbBwb5910Wb0");

        WebClient.ResponseSpec responseSpec = webClient.get()
                .uri(API_ARGUMENTS)
                .headers(httpHeaders -> httpHeaders.putAll(headers))
                .retrieve();

        Goods response = responseSpec.bodyToMono(Goods.class).block();
    }

    private List<Item> fetchItems() {
        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection()))
                .baseUrl(API_URL)
                .build();

        WebClient.ResponseSpec responseSpec = webClient.get()
                .uri(API_ARGUMENTS)
                .retrieve();

        Items response = responseSpec.bodyToMono(Items.class).block();
        return response != null ? response.getData().getItems() : Collections.emptyList();
    }
}
