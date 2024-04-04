package ua.dokat.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ua.dokat.RabbitQueue.*;

@Configuration
public class RabbitConfiguration {

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue textMessageQueue(){
        return new Queue(TEXT_MESSAGE);
    }

    //todo: видимо нахуй не нужен такой тип сообщения. он не используется. команды обрабатываются в типе text_message так как разделение текстового сообщения от команды происходит в consumerservice. есть варик сделать отдельный класс, но тогда разделение команды от сообщения будет в updatecontroller, а нахуй нам это надо, верно?
    @Bean
    public Queue commandMessageQueue(){
        return new Queue(COMMAND_MESSAGE);
    }

    @Bean
    public Queue answerMessageQueue(){
        return new Queue(ANSWER_MESSAGE);
    }

    @Bean
    public Queue notifyMessageQueue(){
        return new Queue(NOTIFY_ORDER_MESSAGE);
    }

    @Bean
    public Queue callbackQueryQueue(){
        return new Queue(CALLBACK_QUERY);
    }
}
