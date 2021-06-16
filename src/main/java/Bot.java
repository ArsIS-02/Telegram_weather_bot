import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {//наследование класса от TelegramLongPollingBot

    public static void main(String[] args) {// точка входа в проект
        ApiContextInitializer.init();//инициализация Api
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();//создание объекта TelegramApi
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {// вывод на экран в случае,когда выбрасывается исключение
            e.printStackTrace();
        }
    }//переопределение методов

    private void sendMsg(Message message, String text) {//метод, описывающий действия робота в ответ на наши сообщения ему
        SendMessage sendMessage = new SendMessage();// инициализация сообщения,отправленного роботу
        sendMessage.enableMarkdown(true);// включение возможности разметки
        sendMessage.setChatId(message.getChatId().toString());//какому чату ответить и на какое сообщение ответить боту
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);//текст,отправленный в метод
        try {
            setButtons(sendMessage);//отправка сообщения
            execute(sendMessage);//устаревший, но работающий метод
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void onUpdateReceived(Update update) {// метод для приёма сообщений(обновление)
        Model model = new Model();//объект модели,отправляемой клиенту
        Message message = update.getMessage();//создание объекта message
        if (message != null && message.hasText()) {//проверка на корректность ввода сообщение(пустое или нет)
            switch (message.getText()) {//реакция бота на нажатие кнопок клиентом
                case "/":
                    sendMsg(message, "Добро пожаловать! Я - бот-помощник, меня написал Арсентьев Игорь для своей курсовой работы, я сообщаю о данных погоды. Вы можете ввести название населённого пункта, и я вам расскажу о температуре воздуха, влажности, наличии осадков.");//сообщение-приветствие пользователю
                    break;
                case "/start":
                    sendMsg(message, "Добро пожаловать! Я - бот-помощник, меня написал Арсентьев Игорь для своей курсовой работы, я сообщаю о данных погоды. Вы можете ввести название населённого пункта, и я вам расскажу о температуре воздуха, влажности, наличии осадков.");//сообщение-приветствие пользователю
                    break;
                    case "Инструкция":// кнопка "Инструкция"
                    sendMsg(message, "Введите корректное название города/посёлка на карте мира, будет отображена погода на данный момент там");//сообщение пользователю,для "красоты и понимания"
                    break;
                case "Настройки"://кнопка "Настройки"
                    sendMsg(message, "Пока нечего настраивать,но если у вас есть идеи, напишите @ArsIS1990! Оставляя обратную связь, вы вносите изменения в сервис, ни одно пожелание не проигнорируется!");// сообщение на кнопке настроек
                    break;
                default://вывод информации с сайта по введённому населённому пункту,если таковой имеется
                    try {
                        sendMsg(message, Weather.getWeather(message.getText(), model));//сообщение клиента и ответ ему с моделью
                    } catch (IOException e) {//сообщение в случае некорректного ввода
                        sendMsg(message, "Введите КОРРЕКТНОЕ название города/посёлка на карте мира!");//сообщение клиенту при ошибке ввода
                    }
            }
        }
    }
    private void setButtons(SendMessage sendMessage) {//метод,описывающий клавиатуру в чате
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();//создание клавиатуры для "красоты и понимания"
        sendMessage.setReplyMarkup(replyKeyboardMarkup);//разметка для клавиатуры
        replyKeyboardMarkup.setSelective(true);//параметр,определяющий,кому выводить клавиатуру на экран-по умолчанию клиенту,который вошёл в чат
        replyKeyboardMarkup.setResizeKeyboard(true);//автоматическая подгонка размера клавиатуры
        replyKeyboardMarkup.setOneTimeKeyboard(false);//скрывать или нет клавиатуру

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
//создание кнопок клавиатуры
        keyboardFirstRow.add(new KeyboardButton("Инструкция"));
        keyboardFirstRow.add(new KeyboardButton("Настройки"));
//список кнопок клавиатуры
        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);//установка списка на клавиатуре
    }

    public String getBotUsername() {// метод для возврата имени,указанного при регистрации
        return "ArsIS_proect_bot";
    }

    public String getBotToken() {//введённый токен,выданный BotFather
        return "1777002745:AAEHuxAGGSkRaO0JhXe7wEl4YHhG7DuNMXc";
    }
}
