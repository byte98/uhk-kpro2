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

import cz.uhk.fim.skodaji1.kpro2.jticket.ui.text.ITextUIHelp;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.text.ITextUIScreen;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.text.TextUIController;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.text.TextUIHTMLTemplateScreen;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.text.TextUIHelpFactory;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing edit station form (with selected name option)
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TextUIStationsEditName extends TextUIState
{
    
    /**
     * Abbreavation of station
     */
    private String stationAbbr = null;
    
    /**
     * Name of station
     */
    private String stationName = null;

    /**
     * Creates new edit station form (with selected name option)
     * @param controller Controller of program
     */
    public TextUIStationsEditName(TextUIController controller)
    {
        super(controller);
        this.commandPrefix = "/data/stations/edit:name";
        this.screen = new TextUIHTMLTemplateScreen("stations-edit-name", "stations-edit-name.html");
        this.name = "stations-edit-name";
        this.strict = false;
        
        this.helps = new ITextUIHelp[3];
        this.helps[0] = TextUIHelpFactory.createSimpleHelp("<nazev stanice>", Color.YELLOW, "Novy nazev stanice");
        this.helps[1] = TextUIHelpFactory.createSimpleHelp("delete", Color.MAGENTA, "Smazat stanici");
        this.helps[2] = TextUIHelpFactory.createSimpleHelp("cancel", Color.MAGENTA, "Zrusit");
        
    }

    @Override
    public void handleInput(String input)
    {
        if ("cancel".equals(input.toLowerCase()))
        {
            this.controller.changeState("stations");   
        }
        else if ("delete".equals(input.toLowerCase()))
        {
            Map<String, String> data = new HashMap<>();
            data.put("station_name", this.stationName);
            data.put("station_abbr", this.stationAbbr);
            this.controller.changeState("stations-delete", data);
        }
        else
        {
            if (cz.uhk.fim.skodaji1.kpro2.jticket.data.Stations.GetInstance().CheckFreeName(input) == false && !input.toLowerCase().equals(this.stationName.toLowerCase()))
            {
                this.controller.showError("Jmeno stanice '" + input + "' jiz existuje!");
            }
            else
            {
                Map<String, String> data = new HashMap<>();
                data.put("station_name", this.stationName);
                data.put("station_abbr", this.stationAbbr);
                data.put("station_new_name", input);
                this.controller.changeState("stations-edit-abbr", data);
            }            
        }
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
    public ITextUIScreen getScreen(Map<String, String> data)
    {
        data.put("stations_tr", cz.uhk.fim.skodaji1.kpro2.jticket.data.Stations.GetInstance().GenerateTableRows());
        this.stationAbbr = data.get("station_abbr");
        this.stationName = data.get("station_name");
        ((TextUIHTMLTemplateScreen)this.screen).setContent(data);
        return this.screen;
    }
}
