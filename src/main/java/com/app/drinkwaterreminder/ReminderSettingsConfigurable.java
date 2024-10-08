package com.app.drinkwaterreminder;

import com.intellij.openapi.application.ApplicationManager;
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
        ReminderSettingsState.getInstance().reminderInterval = (Integer) intervalSpinner.getValue();

        // Save the new interval to the state
        ReminderSettingsState.getInstance().reminderInterval = (Integer) intervalSpinner.getValue();

        // Ask the user if they want to restart the IDE
        int result = Messages.showYesNoDialog(
                "You have changed the reminder interval. A restart of IntelliJ IDEA is required for the changes to take effect. Do you want to restart now?",
                "Restart Required",
                Messages.getQuestionIcon()
        );

        if (result == Messages.YES) {
            ApplicationManager.getApplication().restart();
        }
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
