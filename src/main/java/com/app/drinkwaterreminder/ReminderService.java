package com.app.drinkwaterreminder;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.components.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class ReminderService {

    public final static Logger logger = LoggerFactory.getLogger(ReminderService.class);
    private Timer timer;


    public ReminderService() {
        // Schedule the reminder when the service is initialized
        scheduleReminder();
    }

    public void scheduleReminder() {
        if (timer != null) {
            timer.cancel(); // Cancel the previous timer if it's already running
        }

        timer = new Timer();
        int userInterval = ReminderSettingsState.getInstance().reminderInterval * 60 * 1000; // Convert to milliseconds
        timer.schedule(new WaterReminderTask(), userInterval, userInterval);
    }

    static class WaterReminderTask extends TimerTask {
        @Override
        public void run() {

            Notification notification = new Notification("HydratorIO",
                    "Water reminder",
                    "Time to drink water!",
                    NotificationType.ERROR);

            try {
                ImageIcon icon = new ImageIcon(Objects.requireNonNull(ReminderService.class.getClassLoader().getResourceAsStream("notificationIcon.png")).readAllBytes());
                notification.setIcon(icon);
            } catch (IOException e) {
                logger.error("Failed getting Image ",e);
                e.printStackTrace();
            }

            Notifications.Bus.notify(notification);

            playSound();
        }

        private void playSound() {
            try {
                // Load the sound file from the resources folder
                InputStream audioSrc = ReminderService.class.getClassLoader().getResourceAsStream("water.wav");
                if (audioSrc == null) {
                    throw new IOException("Sound file not found: " + "water.wav");
                }

                BufferedInputStream bufferedIn = new BufferedInputStream(audioSrc);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);

                clip.start();

                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }

    }


}
