package com.app.drinkwaterreminder;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.ui.Messages;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;

public class ReminderSettingsConfigurable implements Configurable {

    private JPanel settingsPanel;
    private JSpinner intervalSpinner;

    @Override
    public String getDisplayName() {
        return "HydratorIO Settings";
    }

    @Override
    public @Nullable JComponent createComponent() {
        settingsPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Create label
        JLabel intervalLabel = new JLabel("Reminder Interval (minutes):");

        // Create spinner for the interval
        intervalSpinner = new JSpinner(new SpinnerNumberModel(
                ReminderSettingsState.getInstance().reminderInterval, 1, 1440, 1));

        // Add components to the topPanel
        topPanel.add(intervalLabel);
        topPanel.add(intervalSpinner);

        settingsPanel.add(topPanel, BorderLayout.NORTH);

        return settingsPanel;
    }

    @Override
    public boolean isModified() {
        int currentInterval = (Integer) intervalSpinner.getValue();
        return currentInterval != ReminderSettingsState.getInstance().reminderInterval;
    }

    @Override
    public void apply() {
        // Save the new interval to the state
        ReminderSettingsState.getInstance().reminderInterval = (Integer) intervalSpinner.getValue();

        // Ask the user if they want to restart the IDE
        Messages.showInfoMessage(
                "You have changed the reminder interval. \nA restart of IntelliJ IDEA is required for the changes to take effect \uD83D\uDE09 .",
                "Restart Required"
        );
    }


    @Override
    public void reset() {
        intervalSpinner.setValue(ReminderSettingsState.getInstance().reminderInterval);
    }

    @Override
    public void disposeUIResources() {
        settingsPanel = null;
        intervalSpinner = null;
    }
}
