package com.masivian.roulette.exception;

public class BetException {

    public static class NumberOutOfRange extends RejectException {
        public NumberOutOfRange() {
            super("The bet number must be between 0 and 36");
        }
    }

    public static class InvalidMoney extends RejectException {
        public InvalidMoney() {
            super("The bet money must be between $0 and $10.000");
        }
    }

    public static class InvalidColor extends RejectException {
        public InvalidColor() {
            super("The valid colors are: black and red");
        }
    }

}
