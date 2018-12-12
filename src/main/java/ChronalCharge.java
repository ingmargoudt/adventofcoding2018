public class ChronalCharge {

    public static final int SERIAL_NUMBER = 4842;

    public static void main(String[] args) {
        FuelCell[][] grid = new FuelCell[300][300];
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid.length; y++) {
                grid[x][y] = new FuelCell(x + 1, y + 1);
            }
        }
        int maxWinLeft = 0;
        int maxWinTop = 0;
        int maxSoore = 0;
        int maxWindowSize = 0;
        for (int windowSize = 1; windowSize <= 300; windowSize++) {
            System.out.println("scanning size " + windowSize);
            for (int windowLeft = 0; windowLeft < grid.length - windowSize; windowLeft++) {
                for (int windowTop = 0; windowTop < grid.length - windowSize; windowTop++) {
                    int windowSum = 0;
                    for (int x = 0; x < windowSize; x++) {
                        for (int y = 0; y < windowSize; y++) {
                            windowSum += grid[windowLeft + x][windowTop + y].getValue();
                        }
                    }
                    if (windowSum > maxSoore) {
                        maxWinLeft = windowLeft;
                        maxWinTop = windowTop;
                        maxSoore = windowSum;
                        maxWindowSize = windowSize;
                        System.out.println("new max : " + maxWinLeft + 1 + "," + maxWinTop + "," + maxWindowSize);
                    }
                }
            }
        }
        System.out.println(maxWinLeft + 1 + "," + maxWinTop + 1 + "," + maxWindowSize);
    }

    static class FuelCell {

        private int x;
        private int y;

        public FuelCell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getValue() {
            int rackId = x + 10;
            int powerLevel = rackId * y;
            powerLevel += ChronalCharge.SERIAL_NUMBER;
            powerLevel *= rackId;
            if (powerLevel < 1000) {
                powerLevel = 0;
            } else {
                powerLevel /= 100;
                powerLevel %= 10;
            }
            powerLevel -= 5;
            return powerLevel;
        }
    }
}