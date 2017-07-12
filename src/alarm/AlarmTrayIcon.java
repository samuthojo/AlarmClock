/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package alarm;

/*
 * AlarmTrayIcon.java
 */
import static alarm.AlarmApp.frame;
import static alarm.AlarmTrayIcon.tray;
import static alarm.AlarmTrayIcon.trayIcon;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class AlarmTrayIcon {

    public static TrayIcon trayIcon = null;

    public static SystemTray tray = SystemTray.getSystemTray();

    private static boolean isadded = false;

    public static void createGUI() {

        if (!isadded) {
            Image image
                    = (new ImageIcon(AlarmTrayIcon.class.getResource("/alarm/resources/alarm"), "tray icon")).getImage();

            trayIcon = new TrayIcon(image);

            final PopupMenu popup = new PopupMenu();

            // Create popup menu components
            MenuItem reset = new MenuItem("Reset");
            MenuItem dismiss = new MenuItem("Dismiss");

            //Add components to popup menu
            popup.add(reset);
            popup.addSeparator(); //popup.addSeparator();
            popup.add(dismiss);

            trayIcon.setPopupMenu(popup);
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("AlarmApp");

            trayIcon.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //Give the frame focus on double click
                    frame.setVisible(true);
                }
            });

            reset.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    AlarmApp.cancel();
                    AlarmApp.getDialog().dispose();
                    frame.setVisible(true);
                }
            });

            dismiss.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //tray.remove(trayIcon);
                    AlarmApp.close();
                }
            });

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                JOptionPane.showMessageDialog(null, "Desktop SystemTray Is Missing!", "Error", ERROR_MESSAGE);
            }
            isadded = true;
        }
    }
}
