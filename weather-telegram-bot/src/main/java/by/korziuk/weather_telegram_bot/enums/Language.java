package by.korziuk.weather_telegram_bot.enums;

public enum Language {

    EN {
        @Override
        public String getName() {
            return "english";
        }

        @Override
        public String getWindSpeed(Unit unit) {
            if (Unit.METRIC.equals(unit)){
                return "m/s";
            }
            return "miles/hr";
        }

        @Override
        public String getFeelsLike() {
            return "feels like";
        }
    },
    RU {
        @Override
        public String getName() {
            return "russian";
        }

        @Override
        public String getWindSpeed(Unit unit) {
            if (Unit.METRIC.equals(unit)){
                return "м/с";
            }
            return "миль/ч";
        }

        @Override
        public String getFeelsLike() {
            return "по ощущениям";
        }
    };

    private Unit unit;

    public abstract String getName();
    public abstract String getWindSpeed(Unit unit);
    public abstract String getFeelsLike();
}
