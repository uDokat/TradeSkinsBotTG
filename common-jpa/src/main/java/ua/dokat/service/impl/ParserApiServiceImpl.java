package ua.dokat.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import ua.dokat.service.ParserApiService;

@Service
@RestController
public class ParserApiServiceImpl implements ParserApiService {

    @Override
    public void sendRequestForAddIdToList(String skinId, String chatId) {
        WebClient.ResponseSpec s = request(skinId, chatId);
        if (s == null) return;
//        System.out.println(s.bodyToMono(String.class).block());
    }

    @PutMapping("/api/test")
    public void test(@RequestParam String skinId, @RequestParam String chatId){
        sendRequestForAddIdToList(skinId, chatId);
    }


    private WebClient.ResponseSpec request(String p, String a){
        try {
            WebClient client = WebClient.create("http://localhost:10101");

            System.out.println("send");

            return client.put()
                    .uri(uriBuilder -> uriBuilder.path("/api/parser/add")
                            .queryParam("skinId", p)
                            .queryParam("chatId", a)
                            .build())
                    .retrieve();
        }catch (Exception e){
            System.out.println(e);
        }

        return null;
    }
}
