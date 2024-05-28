module ua.dokat.jsonentity {
    exports ua.dokat.entity.json;
    exports ua.dokat.entity.json.http;

    requires com.fasterxml.jackson.annotation;
    requires static lombok;
    requires spring.web;
    requires telegrambots.meta;
}