package janorschke.meyer.enums

/**
 * Enum representing time modes for the countdown in milliseconds.
 */
enum class TimeMode(val time: Long) {
    /**
     * Bullet mode: 1 minute time limit.
     */
    BULLET(60000),

    /**
     * Blitz mode: 5 minutes time limit.
     */
    BLITZ(300000),

    /**
     * Rapid mode: 10 minutes time limit.
     */
    RAPID(600000),

    /**
     * Unlimited mode: No time limit.
     */
    UNLIMITED(-1);
}
