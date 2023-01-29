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

import cz.uhk.fim.skodaji1.kpro2.jticket.data.ZoneTariff;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.text.ITextUIHelp;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.text.ITextUIScreen;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.text.TextUIController;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.text.TextUIHTMLTemplateScreen;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.text.TextUIHelpFactory;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing tariff viewer for zone tariffs (with deleting dialog)
 * @author Jiri Skoda <skodaji1@uhk.cz>
 */
public class TextUITariffsZoneDelete extends TextUIState {

    /**
     * Tariff which is displayed
     */
    private ZoneTariff tariff;
    
    /**
     * Creates new tariff viewer for zone tariffs (with deleting dialog)
     * @param controller Controller of program
     */
    public TextUITariffsZoneDelete(TextUIController controller)
    {
        super(controller);
        this.commandPrefix = "/data/tariffs/";
        this.screen = new TextUIHTMLTemplateScreen("tariffs-zone-delete", "tariffs-zone-delete.html");
        this.name = "tariffs-zone-delete";
        this.strict = true;
        
        this.helps = new ITextUIHelp[2];
        this.helps[1] = TextUIHelpFactory.createSimpleHelp("no", Color.GREEN, "Zrusit");
        this.helps[0] = TextUIHelpFactory.createSimpleHelp("yes", Color.RED, "Smazat tarif");
    }
    
    @Override
    public ITextUIScreen getScreen(Map<String, String> data)
    {
        this.tariff =(ZoneTariff) cz.uhk.fim.skodaji1.kpro2.jticket.data.Tariffs.GetInstance().GetTariff(data.get("tariff_abbr"));
        if (tariff != null)
        {
            data.put("tariff_name", this.tariff.getName());
            data.put("tariff_zones", this.tariff.generateZonesTr());
            data.put("tariff_prices", this.getTariffPrices());
            this.commandPrefix = "/data/tariffs/" + this.tariff.getAbbr().toLowerCase() + "/delete?";
        }
        ((TextUIHTMLTemplateScreen)this.screen).setContent(data);
        return this.screen;
    }
    
    @Override
    public void handleInput(String input)
    {
        switch(input.toLowerCase())
        {
            case "no": 
                Map<String, String> data = new HashMap<>();
                data.put("tariff_abbr", this.tariff.getAbbr());
                this.controller.changeState("tariffs-zone-view"); 
                break;
            case "yes":
                String tarifName = this.tariff.getName();
                cz.uhk.fim.skodaji1.kpro2.jticket.data.Tariffs.GetInstance().RemoveTariff(this.tariff);
                this.controller.showSucess("Tarif '" + tarifName + "' byl uspesne odebran.");
                this.controller.changeState("tariffs");
                break;
        }
    }
    
    /**
     * Gets table rows with tariff row prices
     * @return 
     */
    private String getTariffPrices()
    {
        String reti = new String();
        reti = this.tariff.GetAllPrices().keySet().stream().map(zone -> "<tr><td>" + zone + "</td><td style='color: white;'>" + this.tariff.GetAllPrices().get(zone) + " Kc</td></tr").reduce(reti, String::concat);
        return reti;
    }
    
}
