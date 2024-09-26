package config;

import org.aeonbits.owner.Config;

@Config.Sources("file:src/main/resources/configs/emulator.properties")
public interface EmulatorConfig extends Config {
    @Key("automationName")
    String automationName();

    @Key("appWaitActivity")
    String appWaitActivity();

    @Key("fullReset")
    Boolean fullReset();

    @Key("remoteURL")
    String remoteURL();

    @Key("app")
    String app();

    @Key("chromedriverExecutable")
    String chromedriverExecutable();
}
