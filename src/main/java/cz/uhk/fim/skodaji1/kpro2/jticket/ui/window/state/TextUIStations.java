/*
 * Copyright (C) 2021 Jiri Skoda <skodaji1@uhk.cz>
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
package cz.uhk.fim.skodaji1.kpro2.jticket.ui.text.state;

import cz.uhk.fim.skodaji1.kpro2.jticket.data.Station;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.text.ITextUIHelp;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.text.ITextUIScreen;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.text.TextUIController;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.text.TextUIHTMLTemplateScreen;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.text.TextUIHelpFactory;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing state of program which displays stations menu
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TextUIStations extends TextUIState
{

    /**
     * Creates new state of program with stations menu
     * @param controller 
     */
    public TextUIStations(TextUIController controller)
    {
        super(controller);
        this.commandPrefix = "/data/stations";
        this.screen = new TextUIHTMLTemplateScreen("stations", "stations.html");
        this.name = "stations";
        this.strict = false;
        
        this.helps = new ITextUIHelp[3];
        this.helps[0] = TextUIHelpFactory.createSimpleHelp("<nazev nebo zkratka stanice>", Color.YELLOW, "Vybrat stanici");
        this.helps[1] = TextUIHelpFactory.createSimpleHelp("add", Color.YELLOW, "Pridat novou stanici");
        this.helps[2] = TextUIHelpFactory.createSimpleHelp("back", Color.MAGENTA, "Zpet");
    }
    
    @Override
    public ITextUIScreen getScreen()
    {
        Map<String, String> data = new HashMap<>();
        data.put("stations_tr", cz.uhk.fim.skodaji1.kpro2.jticket.data.Stations.GetInstance().GenerateTableRows());
        ((TextUIHTMLTemplateScreen)this.screen).setContent(data);
        return this.screen;
    }

    @Override
    public void handleInput(String input)
    {
        switch (input.toLowerCase())
        {
            case "back": this.controller.changeState("data"); break;
            case "add": this.controller.changeState("stations-add-name"); break;
            default:
                Station st = cz.uhk.fim.skodaji1.kpro2.jticket.data.Stations.GetInstance().GetStation(input);
                if (st == null)
                {
                    this.controller.showError("Stanice '" + input + "' nenalezena!");
                }
                else
                {
                    Map<String, String> data = new HashMap<>();
                    data.put("station_name", st.getName());
                    data.put("station_abbr", st.getAbbrevation());
                    this.controller.changeState("stations-edit-name", data);
                }
                break;
        }
    }
    
}
