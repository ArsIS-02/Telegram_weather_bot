public class Model {//класс с моделью сообщения, выдаваемого роботом
    private String name;//название населённого пункта
    private Double temp;//значение температуры воздуха
    private Double humidity;//значение влажности воздуха
    private String icon;//значение картинки,отображающей статус погоды
    private String main;//значение общего статуса погоды

    //сгенерированные методы для "вытаскивания данных"
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
