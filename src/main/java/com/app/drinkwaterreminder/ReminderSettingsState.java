package com.app.drinkwaterreminder;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

@State(
        name = "ReminderSettingsState",
        storages = @Storage("ReminderSettings.xml")
)
public class ReminderSettingsState implements PersistentStateComponent<ReminderSettingsState> {

    public final static Logger logger = LoggerFactory.getLogger(ReminderSettingsState.class);
    public int reminderInterval = 60;

    public static ReminderSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(ReminderSettingsState.class);
    }

    @Override
    public @Nullable ReminderSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ReminderSettingsState state) {
        logger.info("Reminder interval loaded: " + reminderInterval); // Log loaded interval
        XmlSerializerUtil.copyBean(state, this);
    }
}
