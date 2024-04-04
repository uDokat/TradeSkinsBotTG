package ua.dokat.utils;

import org.springframework.context.annotation.Configuration;

//todo: ну типо не получается yml подключить
@Configuration
public class ResponseConfig {

    public final static String NO_ACCOUNT = """ 
            ❌ Вы не зарегистрированы. ❌
            Команда для регистрации: /registration""";
    public final static String NO_ACTIVE = "❌ Ваш аккаунт не активирован. ❌";
    public final static String ACTION_INC = "ACTION_INC";

    public final static String INPUT_ITEM_ID = "Введите айди скина.";
    public final static String WAIT_ITEM_ID = "Ввод скина уже ожидаетсяю.";
    public final static String ADDED_RESPONSE = "Успешно! Скин добавлен в список просматриваемых. ✅";
    public final static String ALREADY_ON_LIST = "Скин уже находится в списке просматриваемых.";
    public final static String ITEM_NOT_FOUND = "❌ Ошибка. Скин не найден. ❌";

    public final static String ITEMS_LIST = "items";
    public final static String DONT_HAVE_LIST = "DONT_HAVE_LIST";

    public final static String GOOD_REGISTRATION = """
            Ваш аккаунт успешно зарегистрирован.
            ❗❗Для начала работы, пожалуйста, установите  /cookie и /token аккаунта Buff.
            Это необходимо для дальшей работы с сайтом.❗❗""";
    public final static String ALREADY_REGISTERED = "Регистрация уже завершена.";

    public final static String RELOADED = "RELOADED";
    public final static String ERROR_RELOAD = "ERROR_RELOAD";

    public final static String INPUT_COOKIE = "Введите куки.";
    public final static String WAIT_COOKIE = "Ввод куки уже ожидается.";
    public final static String COOKIES_SET = "Успешно! Куки установлены. ✅";

    public final static String INPUT_TOKEN = "Введите токен.";
    public final static String WAIT_TOKEN = "Ввод токена уже ожидается.";
    public final static String TOKEN_SET = "Успешно! Токен установлен. ✅";
}
