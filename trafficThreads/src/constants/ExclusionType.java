package constants;

public enum ExclusionType {
        MONITOR(1),
        SEMAPHORE(2),
        SOCKTES(3);

        private final int value;

        ExclusionType(int value) {
            this.value = value;
        }
}
