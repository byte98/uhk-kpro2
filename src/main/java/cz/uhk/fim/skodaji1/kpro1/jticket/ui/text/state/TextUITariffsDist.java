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
package cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.state;

import cz.uhk.fim.skodaji1.kpro1.jticket.data.DistanceTariff;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.ITextUIHelp;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.ITextUIScreen;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIController;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIHTMLTemplateScreen;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.text.TextUIHelpFactory;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing creating new distance tariff (with confirmation dialog)
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TextUITariffsDist extends TextUIState {

    /**
     * Name of tariff
     */
    private String tariffName;
    
    /**
     * Abbreavation of tariff
     */
    private String tariffAbbr;
    
    /**
     * Creates new dialog for creating new distance tariff (with confirmation dialog)
     * @param controller Controller of program
     */
    public TextUITariffsDist(TextUIController controller)
    {
        super(controller);
        this.commandPrefix = "/data/tariffs/distance?";
        this.screen = new TextUIHTMLTemplateScreen("tariffs-dist", "tariffs-dist.html");
        this.name = "tariffs-dist";
        this.strict = true;
        
        this.helps = new ITextUIHelp[2];
        this.helps[0] = TextUIHelpFactory.createSimpleHelp("yes", Color.GREEN, "Ano, zadane udaje jsou v poradku");
        this.helps[1] = TextUIHelpFactory.createSimpleHelp("no", Color.RED, "Zrusit");
    }

    @Override
    public ITextUIScreen getScreen()
    {
        Map<String, String> data = new HashMap<>();
        data.put("tariffs_tr", cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariffs.GetInstance().GenerateTariffsTableRows());
        ((TextUIHTMLTemplateScreen)this.screen).setContent(data);
        return this.screen;
    }
    
    @Override
    public ITextUIScreen getScreen(Map<String, String> data)
    {
        data.put("tariffs_tr", cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariffs.GetInstance().GenerateTariffsTableRows());
        this.tariffName = data.get("tariff_name");
        this.tariffAbbr = data.get("tariff_abbr");
        ((TextUIHTMLTemplateScreen)this.screen).setContent(data);
        return this.screen;
    }
    
    @Override
    public void handleInput(String input)
    {
        switch(input.toLowerCase())
        {
            case "no": this.controller.changeState("tariffs"); break;
            case "yes": 
                Map<String, String> data = new HashMap<>();
                data.put("tariff_name", this.tariffName);
                data.put("tariff_abbr", this.tariffAbbr);                
                System.out.format("Tariff (type: %s, name: %s, abbreavation: %s) has been created\n", "DISTANCE", this.tariffName, this.tariffAbbr);
                cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariffs.GetInstance().AddTariff(new DistanceTariff(this.tariffName, this.tariffAbbr));
                this.controller.changeState("tariffs-dist-prices", data);
                break;
        }
    }
    
}
