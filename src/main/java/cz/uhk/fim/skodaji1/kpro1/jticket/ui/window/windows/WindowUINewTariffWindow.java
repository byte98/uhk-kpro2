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

import cz.uhk.fim.skodaji1.kpro1.jticket.data.Configuration;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.DistanceTariff;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Stations;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.Tariff;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.TariffType;
import cz.uhk.fim.skodaji1.kpro1.jticket.data.ZoneTariff;
import cz.uhk.fim.skodaji1.kpro1.jticket.jTicket;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.window.WindowUI;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.window.WindowUITheme;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.window.tables.WindowUIPricesTableModel;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.window.tables.WindowUIZonesTableModel;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.window.windows.dialogs.WindowUIButtonType;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.window.windows.dialogs.WindowUIDialog;
import cz.uhk.fim.skodaji1.kpro1.jticket.ui.window.windows.dialogs.WindowUIDialogType;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 * Class representing new tariff window
 * @author Jiri Skoda <skodaji1@uhk.cz.cz>
 */
public class WindowUINewTariffWindow extends JDialog
{
    /**
     * Panel with first stage of wizard
     */
    private JPanel stage1;
    
    /**
     * Panel with second stage of wizard
     */
    private JPanel stage2;
    
    /**
     * Text field containing tariffs name
     */
    private JTextField tariffName;
    
    /**
     * Text field containing tariffs abbreavation
     */
    private JTextField tariffAbbr;
    
    /**
     * Type of tariff
     */
    private TariffType tariffType = TariffType.ZONE;
    
    /**
     * Table holding price list
     */
    private JTable priceTable;
    
    /**
     * Table holding zones assignments
     */
    private JTable zoneTable;
    
    /**
     * Flag, whether wizard has been finished using 'OK' button
     */
    private boolean okClicked = false;
    
    /**
     * Content of window
     */
    private JPanel content;
    
    /**
     * Creates new window for creating new tariff
     */
    public WindowUINewTariffWindow()
    {
        super.setTitle("Pr??vodce vytvo??en??m nov??ho tarifu");
        super.setIconImage(new ImageIcon(WindowUI.UI_PATH + "/newitem.png").getImage());
        super.setModal(true);
        this.initializeComponents();
        super.setSize(new Dimension(650, 450));
        super.getInsets().set(20, 20, 20, 20);
        super.setResizable(false);
        super.setLocation(
                ((Toolkit.getDefaultToolkit().getScreenSize().width - super.getWidth()) / 2),
                ((Toolkit.getDefaultToolkit().getScreenSize().height - super.getHeight()) / 2)

        );
        super.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent ev)
            {
                WindowUIDialog dialog = new WindowUIDialog(
                    "Ukon??it pr??vodce",
                    "Opravdu chcete ukon??it pr??vodce vytvo??en??m nov??ho tarifu?",
                    WindowUIDialogType.QUESTION,
                    WindowUIButtonType.YES,
                    WindowUIButtonType.NO
                );
                dialog.showDialog();
                if (dialog.getResult() == WindowUIButtonType.YES)
                {
                    okClicked = false;
                    dispose();
                }
            }
        });
    }
    
    /**
     * Initializes components of wizard
     */
    private void initializeComponents()
    {
        final int PADDING = 10;
        //<editor-fold defaultstate="collapsed" desc="Content of dialog">
        this.content = new JPanel(new CardLayout());
        super.getContentPane().add(this.content);
        //<editor-fold defaultstate="collapsed" desc="First stage">
        this.stage1 = new JPanel(new BorderLayout());
        this.stage1.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        //<editor-fold defaultstate="collapsed" desc="Image of stage">
        JLabel stage1Img = new JLabel();
        stage1Img.setIcon(new ImageIcon(WindowUI.UI_PATH + "/tariffwiz-1.png"));
        stage1Img.setSize(100, 300);
        stage1Img.setBackground(WindowUITheme.getTheme(Configuration.getInstance(jTicket.CONFIG_FILE).uiTheme).getTariffWizardColour());
        stage1Img.setOpaque(true);
        stage1Img.setBorder(BorderFactory.createLoweredBevelBorder());
        this.stage1.add(stage1Img, BorderLayout.WEST);
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Content of stage">
        JPanel stage1Content = new JPanel();
        stage1Content.setLayout(new BoxLayout(stage1Content, BoxLayout.Y_AXIS));
        stage1Content.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        stage1Content.add(new JLabel("V??tejte v pr??vodci vytvo??en??m nov??ho tarifu."));
        stage1Content.add(new JLabel("Tento pr??vodce V??m umo??n?? vytvo??it krok za krokem nov?? tarif."));
        stage1Content.add(new JLabel(System.lineSeparator()));
        JLabel basicInfoLabel = new JLabel("1) Z??kladn?? informace o tarifu");
        basicInfoLabel.setFont(basicInfoLabel.getFont().deriveFont(Font.BOLD));
        stage1Content.add(basicInfoLabel);
        stage1Content.add(new JLabel("V tomto kroku bude zapot??eb?? zadat z??kladn?? informace t??kaj??c?? se nov??ho tarifu."));
        stage1Content.add(new JLabel(System.lineSeparator()));
        stage1Content.add(new JLabel("Zkratka tarifu"));
        this.tariffAbbr = new JTextField();
        stage1Content.add(this.tariffAbbr);
        stage1Content.add(new JLabel(System.lineSeparator()));
        stage1Content.add(new JLabel("N??zev tarifu"));
        this.tariffName = new JTextField();
        stage1Content.add(this.tariffName);
        stage1Content.add(new JLabel(System.lineSeparator()));
        stage1Content.add(new JLabel("Druh tarifu"));
        ButtonGroup typeGroup = new ButtonGroup();
        JRadioButton zoneRadio = new JRadioButton("Z??NOV??", true);
        zoneRadio.addActionListener((e) -> {
            if (zoneRadio.isSelected())
            {
                this.tariffType = TariffType.ZONE;
            }
        });
        typeGroup.add(zoneRadio);
        stage1Content.add(zoneRadio);
        stage1Content.add(new JLabel("Fin??ln?? cena j??zdenky je ur??ena po??tem z??n mezi v??choz?? a c??lovou stanic??."));
        stage1Content.add(new JLabel(System.lineSeparator()));
        JRadioButton distanceRadio = new JRadioButton("VZD??LENOSTN??");
        distanceRadio.addActionListener((e) -> {
            if (distanceRadio.isSelected())
            {
                this.tariffType = TariffType.DISTANCE;
            }
        });
        typeGroup.add(distanceRadio);
        stage1Content.add(distanceRadio);
        stage1Content.add(new JLabel("Celkov?? cena j??zdenky je vypo??tena ze vzd??lenosti mezi v??choz?? a c??lovou stan??c??."));
        this.stage1.add(stage1Content, BorderLayout.CENTER);
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Buttons of stage">
        JPanel stage1Btns = new JPanel(new FlowLayout(FlowLayout.TRAILING, 10, 10));
        //<editor-fold defaultstate="collapsed" desc="Next button">
        JButton nextBtn = new JButton("Dal????");
        nextBtn.addActionListener((e) -> {
            this.initializeStage2();
            CardLayout cl = (CardLayout)(this.content.getLayout());
            cl.show(this.content, "Dokon??it");
        });
        stage1Btns.add(nextBtn);
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Cancel button">
        JButton cancelBtn = new JButton("Zru??it");
        cancelBtn.addActionListener((e) -> {
            WindowUIDialog dialog = new WindowUIDialog(
                    "Ukon??it pr??vodce",
                    "Opravdu chcete ukon??it pr??vodce vytvo??en??m nov??ho tarifu?",
                    WindowUIDialogType.QUESTION,
                    WindowUIButtonType.YES,
                    WindowUIButtonType.NO
            );
            dialog.showDialog();
            if (dialog.getResult() == WindowUIButtonType.YES)
            {
                this.okClicked = false;
                this.dispose();
            }
        });
        stage1Btns.add(cancelBtn);
        //</editor-fold>
        this.stage1.add(stage1Btns, BorderLayout.SOUTH);
        //</editor-fold>
        this.content.add(this.stage1, "Z??kladn?? informace");
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Second stage">
        this.stage2 = new JPanel(new BorderLayout());
        this.stage2.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        this.content.add(this.stage2, "Dokon??it");
        this.initializeStage2();
        //</editor-fold>
        //</editor-fold>
    }
    
    /**
     * Initializes components for second stage
     */
    private void initializeStage2()
    {
        final int PADDING = 10;
        this.stage2.removeAll();
        
        //<editor-fold defaultstate="collapsed" desc="Image of stage">
        JLabel stage2Img = new JLabel();
        stage2Img.setIcon(new ImageIcon(WindowUI.UI_PATH + "/tariffwiz-2.png"));
        stage2Img.setSize(100, 300);
        stage2Img.setBackground(WindowUITheme.getTheme(Configuration.getInstance(jTicket.CONFIG_FILE).uiTheme).getTariffWizardColour());
        stage2Img.setOpaque(true);
        stage2Img.setBorder(BorderFactory.createLoweredBevelBorder());
        this.stage2.add(stage2Img, BorderLayout.WEST);
        //</editor-fold>
        //<editor-fold defaultststate="collapsed" desc="Content of stage">
        JPanel stage2Content = new JPanel();
        stage2Content.setLayout(new BoxLayout(stage2Content, BoxLayout.Y_AXIS));
        stage2Content.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        String[] cols = new String[]{
            (this.tariffType == TariffType.ZONE ? "PO??ET Z??N" : "VZD??LENOST"),
            "CENA (v K??)"
        };
        //<editor-fold defaultstate="collapsed" desc="Table with zones">
        if (this.tariffType == TariffType.ZONE)
        {
            JLabel zonesLabel = new JLabel("2) P??i??azen?? z??n");
            zonesLabel.setFont(zonesLabel.getFont().deriveFont(Font.BOLD));
            stage2Content.add(zonesLabel);
            stage2Content.add(new JLabel("V n??sleduj??c??m kroku je nezbytn?? p??i??adit ka??dou stanici do z??ny."));
            this.zoneTable = new JTable();
            this.zoneTable.setModel(new WindowUIZonesTableModel(
                    WindowUIZonesTableModel.getEmptyDataVector(),
                    new String[]{"ZKRATKA", "STANICE", "Z??NA"}
            ));
            this.zoneTable.setAutoCreateRowSorter(false);
            this.zoneTable.getTableHeader().setReorderingAllowed(false);
            this.zoneTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            this.zoneTable.getModel().addTableModelListener((e) -> {
                this.priceTable.setModel(new WindowUIPricesTableModel(
                (this.tariffType == TariffType.DISTANCE
                        ? WindowUIPricesTableModel.getEmptyDataVector()
                        : WindowUIPricesTableModel.getEmptyDataVector(this.getMaximalZone())
                        ),
                cols));
            });
            JScrollPane zoneScroll = new JScrollPane(this.zoneTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            stage2Content.add(zoneScroll);
        }
        //</editor-fold>
        JLabel priceLabel = new JLabel((this.tariffType == TariffType.DISTANCE ? "2" : "3") + ") Cen??k tarifu");
        priceLabel.setFont(priceLabel.getFont().deriveFont(Font.BOLD));
        stage2Content.add(priceLabel);
        stage2Content.add(new JLabel("Ve " + (this.tariffType == TariffType.DISTANCE ? "druh??m" : "t??et??m") + " kroku je nutn?? nastavit cen??k tarifu."));
        stage2Content.add(new JLabel("Tento cen??k bude pou??it p??i v??po??tu ceny j??zdenky."));
        stage2Content.add(new JLabel(System.lineSeparator()));
        stage2Content.add(new JLabel("Cen??k tarifu '" + this.tariffName.getText() + "'"));
        //<editor-fold defaultstate="collapsed" desc="Table with pricelist">
        this.priceTable = new JTable();
        
        this.priceTable.setModel(new WindowUIPricesTableModel(
                (this.tariffType == TariffType.DISTANCE
                        ? WindowUIPricesTableModel.getEmptyDataVector()
                        : WindowUIPricesTableModel.getEmptyDataVector(this.getMaximalZone())
                        ),
                cols));
        this.priceTable.setAutoCreateRowSorter(false);
        this.priceTable.getTableHeader().setReorderingAllowed(false);
        this.priceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScroll = new JScrollPane(this.priceTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        stage2Content.add(tableScroll);
        //</editor-fold>
        this.stage2.add(stage2Content);
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Buttons of stage">
        JPanel stage2Btns = new JPanel(new FlowLayout(FlowLayout.TRAILING, 10, 10));
        //<editor-fold defaultstate="collapsed" desc="Previous button">
        JButton prevBtn = new JButton("P??edchoz??");
        prevBtn.addActionListener((e) -> {
            CardLayout cl = (CardLayout)(this.content.getLayout());
            cl.show(this.content, "Z??kladn?? informace");
        });
        stage2Btns.add(prevBtn);
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Finish button">
        JButton finishBtn = new JButton("Dokon??it");
        finishBtn.addActionListener((e) -> {
            this.okClicked = true;
            this.dispose();
        });
        stage2Btns.add(finishBtn);
        //</editor-fold>        
        //<editor-fold defaultstate="collapsed" desc="Cancel button">
        JButton cancelBtn2 = new JButton("Zru??it");
        cancelBtn2.addActionListener((e) -> {
            WindowUIDialog dialog = new WindowUIDialog(
                    "Ukon??it pr??vodce",
                    "Opravdu chcete ukon??it pr??vodce vytvo??en??m nov??ho tarifu?",
                    WindowUIDialogType.QUESTION,
                    WindowUIButtonType.YES,
                    WindowUIButtonType.NO
            );
            dialog.showDialog();
            if (dialog.getResult() == WindowUIButtonType.YES)
            {
                this.okClicked = false;
                this.dispose();
            }
        });
        stage2Btns.add(cancelBtn2);
        //</editor-fold>
        this.stage2.add(stage2Btns, BorderLayout.SOUTH);
        //</editor-fold>
    }
    
    /**
     * Gets maximal number of zone
     * @return Maximal number of zone
     */
    private int getMaximalZone()
    {
        int reti = Integer.MIN_VALUE;
        if (this.zoneTable != null)
        {
            for (int r = 0; r < this.zoneTable.getModel().getRowCount(); r++)
            {
                int val = Integer.MIN_VALUE;
                try
                {
                    val = Integer.parseInt(this.zoneTable.getModel().getValueAt(r, 2).toString());
                }
                catch (Exception ex){}
                if (val > reti)
                {
                    reti = val;
                }
            }
        }
        else
        {
            reti = 0;
        }
        return reti;
    }
    
    /**
     * Checks, whether dialog has been closed by clicking on 'OK' button
     * @return <code>TRUE</code>, if dialog has been closed by clicking on 'OK'
     *         button, <code>FALSE</code> otherwise
     */
    public boolean getOKClicked()
    {
        return this.okClicked;
    }
    
    /**
     * Gets tariff created by dialog
     * @return New tariff created by dialog
     */
    public Tariff getTariff()
    {
        Tariff reti = null;
        if (this.tariffType == TariffType.DISTANCE)
        {
            DistanceTariff ret = new DistanceTariff(
                    this.tariffName.getText(),
                    this.tariffAbbr.getText()
            );
            for (int r = 0; r < this.priceTable.getModel().getRowCount(); r++)
            {
                int price = 0;
                try
                {
                    price = Integer.parseInt(
                            this.priceTable.getModel().getValueAt(r, 
                                    this.priceTable.getModel().getColumnCount() - 1)
                            .toString()
                    );
                }
                catch(Exception ex){}
                ret.SetPrice(r, price);
                
            }
            reti = ret;
        }
        else if (this.tariffType == TariffType.ZONE)
        {
            ZoneTariff ret = new ZoneTariff(
                    this.tariffName.getText(),
                    this.tariffAbbr.getText()
            );
            // Set zones
            for (int r = 0; r < this.zoneTable.getModel().getRowCount(); r++)
            {
                int zone = 0;
                try
                {
                    zone = Integer.parseInt(
                            this.zoneTable.getModel().getValueAt(
                                    r,
                                    this.zoneTable.getColumnCount() - 1
                            ).toString()
                    );
                            
                }
                catch (Exception ex){}
                ret.setZone(Stations.GetInstance().GetStation(this.zoneTable.getModel().getValueAt(r, 0).toString()), zone);
            }
            // Set prices
            for (int r = 0; r < this.priceTable.getModel().getRowCount(); r++)
            {
                int price = 0;
                try
                {
                    price = Integer.parseInt(
                            this.priceTable.getModel().getValueAt(r, 
                                    this.priceTable.getModel().getColumnCount() - 1)
                            .toString()
                    );
                }
                catch(Exception ex){}
                ret.SetPrice(r, price);                
            }
            reti = ret;
        }
        return reti;
    }
}
