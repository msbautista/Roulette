package com.masivian.roulette.exception;

public class RouletteException {

    public static class RouletteIsNotOpen extends RejectException {

        public RouletteIsNotOpen() {
            super("The roulette is not open");
        }
    }

    public static class RouletteDoesNotExist extends RejectException {

        public RouletteDoesNotExist() {
            super("The roulette does not exists");
        }
    }

    public static class RouletteIsClosed extends RejectException {

        public RouletteIsClosed() {
            super("The roulette is closed");
        }
    }


}

