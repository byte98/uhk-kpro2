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
package cz.uhk.fim.skodaji1.kpro2.jticket.ui.text;

import java.awt.Color;

/**
 * Class representing help which will be displayed on screen
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public interface ITextUIHelp
{
    /**
     * Gets command to which help belongs to
     * @return Command to which help belongs to
     */
    public String getCommand();
    
    /**
     * Gets text of help to the command
     * @return Text of help to the command
     */
    public String getHelp();
    
    /**
     * Gets colour of command
     * @return colour of command
     */
    public Color getColor();
}
