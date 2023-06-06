package janorschke.meyer.enums

/**
 * SettingKeys to load and save settings
 */
enum class SettingKeys(val value: String) {
    SETTINGS_SHARED_PREF_TAG("AppSettings"),
    SETTINGS_SAVED_PLAYER_NAME("PlayerName")
}