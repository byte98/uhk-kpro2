/*
 * Copyright (C) 2023 Jiri Skoda <skodaji1@uhk.cz.cz>
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
package cz.uhk.fim.skodaji1.kpro1.jticket.ui.window.windows;

import java.awt.Component;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 * Renderer of list items with images
 * @author Jiri Skoda <skodaji1@uhk.cz.cz>
 */
public class WindowUIImageListCellRenderer extends DefaultListCellRenderer
{
    /**
     * Map of images assigned to items
     */
    private final Map<String, ImageIcon> imageMap;
    
    /**
     * Creates new renderer of list items with images
     * @param m Map of images assigned to items
     */
    public WindowUIImageListCellRenderer(Map<String, ImageIcon> m)
    {
        this.imageMap = m;
    }
    
    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {

        JLabel reti = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);
        reti.setIcon(this.imageMap.get((String) value));
        reti.setHorizontalTextPosition(JLabel.RIGHT);
        reti.setFont(reti.getFont().deriveFont((float)16));
        reti.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 60));
        return reti;
    }
    
}
