import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
public class Weather {
    public static String getWeather(String message, Model model) throws IOException {// "извлечение информации" с web-страницы ресурса
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=6fff53a641b9b9a799cfd6b079f5cd4e");
        //запрос по городу,который мы присвоили message
        Scanner in = new Scanner((InputStream) url.getContent());//запуск объекта Scanner
        String result = "";//создание переменной для вывода информации
        while (in.hasNext()) {// пробегаем циклом по запрашиваемым данным
            result += in.nextLine();//формируем строки для вывода информации
        }
        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));//присваивание значений,"извлечённых" с web-страницы ресурса
        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));
        JSONArray getArray = object.getJSONArray("weather");//формирование массива данных и проход по нему
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);//объект, вытаскиваемый из массива данных с web-страницы
            model.setIcon((String) obj.get("icon"));//иконка для отображения статуса погоды
            model.setMain((String) obj.get("main"));//описание погоды
        }
//вывод сообщения для пользователя,для "красоты и понимания" с указанием интересующей информации
        return "Place: " + model.getName() + "\n" +
                "Temperature: " + model.getTemp() + "C" + "\n" +
                "Humidity:" + model.getHumidity() + "%" + "\n" +
                "Weather: " + model.getMain() + "\n" +
                "http://openweathermap.org/img/w/" + model.getIcon() + ".png";
    }
}
