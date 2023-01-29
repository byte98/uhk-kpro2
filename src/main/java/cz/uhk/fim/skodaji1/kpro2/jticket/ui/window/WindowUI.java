/*
 * Copyright (C) 2023 Jiri Skoda <skodaji1@uhk.cz>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cz.uhk.fim.skodaji1.kpro2.jticket.ui.window;

import cz.uhk.fim.skodaji1.kpro2.jticket.data.Configuration;
import cz.uhk.fim.skodaji1.kpro2.jticket.jTicket;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.IUserInterface;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.window.windows.WindowUIMainWindow;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.window.windows.dialogs.WindowUIExceptionDialog;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Class representing user interface which uses windows to display content
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class WindowUI implements IUserInterface
{
    /**
     * Main window of program
     */
    private WindowUIMainWindow mainWindow;
        
    /**
     * Path to user interface theme resources
     */
    public static String UI_PATH;
    
    @Override
    public void prepare()
    { 
        Configuration config = Configuration.getInstance(jTicket.CONFIG_FILE);
        WindowUITheme theme = WindowUITheme.getTheme(config.uiTheme);
        if (theme == null)
            theme = WindowUITheme.THEMES.get(0);
        WindowUI.UI_PATH = theme.getUiPath();
        try
        {
            UIManager.setLookAndFeel(theme.getLookAndFeel());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
        {
            Logger.getLogger(WindowUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start()
    {
        // Run UI in separate thread
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run()
            {
                mainWindow = new WindowUIMainWindow();
                mainWindow.pack();
                mainWindow.setVisible(true);
            }
        });
    }
    
    /**
     * Method which handles exception
     * @param ex Exception which should be handled
     */
    public static void handleException(Exception ex)
    {
        WindowUIExceptionDialog dialog = new WindowUIExceptionDialog(ex);
        dialog.showDialog();
    }
}
