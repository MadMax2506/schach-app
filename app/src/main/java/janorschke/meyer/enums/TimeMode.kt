package janorschke.meyer.enums

/**
 * Consideration time of a player in a game
 */
enum class TimeMode(val time: Long?) {
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
    UNLIMITED(null);
}
