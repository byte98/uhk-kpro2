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
package cz.uhk.fim.skodaji1.kpro2.jticket.ui.window.windows;

import cz.uhk.fim.skodaji1.kpro2.jticket.data.Configuration;
import cz.uhk.fim.skodaji1.kpro2.jticket.data.UIMode;
import cz.uhk.fim.skodaji1.kpro2.jticket.jTicket;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.window.WindowUI;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.window.WindowUITheme;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.window.windows.dialogs.WindowUIButtonType;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.window.windows.dialogs.WindowUIDialog;
import cz.uhk.fim.skodaji1.kpro2.jticket.ui.window.windows.dialogs.WindowUIDialogType;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Class representing window with settings
 * @author Jiri Skoda <skodaji1@uhk.cz.cz>
 */
public class WindowUISettingsWindow extends JDialog
{
    /**
     * Reference to configuration of program
     */
    private final Configuration config;
    
    /**
     * Padding inside components
     */
    private static final int PADDING = 5;
    
    /**
     * Margin between components
     */
    private static final int MARGIN = 10;
    
    /**
     * Colour used for warning texts
     */
    private static final Color WARNING = new Color(200, 0, 0);
    
    /**
     * Spinner holding ticket validity
     */
    private JSpinner ticketValidity;
    
    /**
     * Text field holding path to directory where all tickets will be printed into
     */
    private JTextField ticketOutput;
    
    /**
     * Spinner holding VAT rate
     */
    private JSpinner ticketVAT;
    
    /**
     * Text field holding path to file with ticket template
     */
    private JTextField ticketTemplate;
    
    /**
     * Text field holding path to file with ticket background
     */
    private JTextField ticketBackground;
    
    /**
     * Spinner holding counter of issued tickets
     */
    private JSpinner ticketIssued;
    
    /**
     * Radio button for graphics application mode
     */
    private JRadioButton appearanceModeGraphics;
    
    /**
     * Radio button for text application mode
     */
    private JRadioButton appearanceModeText;
    
    /**
     * Combo box holding selected user interface theme
     */
    private JComboBox<String> appearanceTheme;
    
    /**
     * Map of themes names and displayNames
     */
    private Map<String, String> themeMap;
    
    /**
     * Creates new window with settings
     * @param c Actual configuration of program
     */
    public WindowUISettingsWindow(Configuration c)
    {
        super(null, java.awt.Dialog.ModalityType.TOOLKIT_MODAL);
        this.config = c;
        this.themeMap = new HashMap<>();
        for (WindowUITheme t: WindowUITheme.THEMES)
        {
            this.themeMap.put(t.getDisplayName(), t.getName());
        }
        super.setTitle("jTicket - Nastaven?? programu");
        super.setIconImage(new ImageIcon(WindowUI.UI_PATH + "/settings.png").getImage());
        super.setSize(new Dimension(WindowUISettingsWindow.WIDTH, WindowUISettingsWindow.HEIGHT));
        this.initializeComponents();
        super.setSize(750, 800);
        super.setModal(true);
        super.setResizable(false);
        super.setLocation(
                ((Toolkit.getDefaultToolkit().getScreenSize().width - super.getSize().width) / 2),
                ((Toolkit.getDefaultToolkit().getScreenSize().height - super.getSize().height) / 2)

        );
    }
    
    /**
     * Initializes components in window
     */
    private void initializeComponents()
    {
        //<editor-fold defaultstate="collapsed" desc="Content of window">
        super.getContentPane().setLayout(new BorderLayout(
                WindowUISettingsWindow.MARGIN,
                WindowUISettingsWindow.MARGIN));
        final Font HEADER = super.getContentPane().getFont().deriveFont(Font.BOLD).deriveFont((float)18);
        final Font ITEM = super.getContentPane().getFont().deriveFont(Font.BOLD);
        JPanel content = new JPanel(new CardLayout());
        super.getContentPane().add(content, BorderLayout.CENTER);
            //<editor-fold defaultstate="collapsed" desc="Ticket settings">
            JPanel ticketsSettings = new JPanel();
            ticketsSettings.setLayout(new BoxLayout(ticketsSettings, BoxLayout.Y_AXIS));
            ticketsSettings.setBorder(BorderFactory.createEmptyBorder(
                WindowUISettingsWindow.PADDING,
                WindowUISettingsWindow.PADDING,
                WindowUISettingsWindow.PADDING,
                WindowUISettingsWindow.PADDING));
                //<editor-fold defaultstate="collapsed" desc="Header">
                JPanel ticketHeader = new JPanel(new FlowLayout(
                        FlowLayout.LEADING,
                        WindowUISettingsWindow.MARGIN,
                        WindowUISettingsWindow.MARGIN
                ));
                ticketHeader.add(new JLabel(new ImageIcon(WindowUI.UI_PATH + "/settings-tickets.png")));
                JLabel ticketHeaderLabel = new JLabel("Nastaven?? j??zdenek");
                ticketHeaderLabel.setFont(HEADER);
                ticketHeader.add(ticketHeaderLabel);
                ticketsSettings.add(ticketHeader);
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Validity">
                JLabel ticketValidityLabel = new JLabel("Platnost j??zdenek");
                ticketValidityLabel.setFont(ITEM);
                ticketValidityLabel.setBorder(BorderFactory.createEmptyBorder(
                        WindowUISettingsWindow.MARGIN,
                        0, 0, 0
                ));
                ticketsSettings.add(ticketValidityLabel);
                ticketsSettings.add(new JLabel("Nastav?? platnost j??zdenek jako po??et hodin od jejich vyd??n??."));
                JPanel ticketValidityPanel = new JPanel(new FlowLayout(
                        FlowLayout.LEADING
                ));
                this.ticketValidity = new JSpinner(new SpinnerNumberModel(this.config.ticketValidity, 0, Integer.MAX_VALUE, 1));
                ticketValidityPanel.add(this.ticketValidity);
                ticketValidityPanel.add(new JLabel("hodin"));
                ticketsSettings.add(ticketValidityPanel);
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Output">
                JLabel ticketOutputLabel = new JLabel("??lo??i??t?? j??zdenek");
                ticketOutputLabel.setFont(ITEM);
                ticketOutputLabel.setBorder(BorderFactory.createEmptyBorder(
                        WindowUISettingsWindow.MARGIN,
                        0, 0, 0
                ));
                ticketsSettings.add(ticketOutputLabel);
                ticketsSettings.add(new JLabel("Nastav?? cestu k adres????i, do kter??ho se budou ukl??dat v??echny j??zdenky."));
                JPanel ticketOutputPanel = new JPanel(new FlowLayout(
                        FlowLayout.LEADING
                ));
                
                File ticketOutputFile = new File(this.config.outputDirectory);
                this.ticketOutput = new JTextField();
                this.ticketOutput.setText(ticketOutputFile.getAbsolutePath());
                ticketOutputPanel.add(this.ticketOutput);
                JButton ticketOutputButton = new JButton();
                ticketOutputButton.setIcon(new ImageIcon(WindowUI.UI_PATH + "/settings-search.png"));
                ticketOutputButton.addActionListener((e) -> {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Vyberte adres???? pro ukl??d??n?? j??zdenek");
                    chooser.setCurrentDirectory(ticketOutputFile);
                    chooser.setAcceptAllFileFilterUsed(false);
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
                    {
                        File chosen = chooser.getSelectedFile();
                        this.ticketOutput.setText(chosen.getAbsolutePath());
                    }
                });
                ticketOutputPanel.add(ticketOutputButton);
                ticketsSettings.add(ticketOutputPanel);
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="VAT">
                JLabel ticketVATLabel = new JLabel("Da?? z p??idan?? hodnoty");
                ticketVATLabel.setFont(ITEM);
                ticketVATLabel.setBorder(BorderFactory.createEmptyBorder(
                        WindowUISettingsWindow.MARGIN,
                        0, 0, 0
                ));
                ticketsSettings.add(ticketVATLabel);
                ticketsSettings.add(new JLabel("Nastav?? aktu??ln?? platnou sazbu dan?? z p??idn?? hodnoty v procentech."));
                JPanel ticketVATPanel = new JPanel(new FlowLayout(
                        FlowLayout.LEADING
                ));
                this.ticketVAT = new JSpinner(new SpinnerNumberModel(this.config.VAT, 0, Integer.MAX_VALUE, 0.01));
                ticketVATPanel.add(this.ticketVAT);
                ticketVATPanel.add(new JLabel("%"));
                ticketsSettings.add(ticketVATPanel);
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Template">
                JLabel ticketTemplateLabel = new JLabel("??ablona");
                ticketTemplateLabel.setFont(ITEM);
                ticketTemplateLabel.setBorder(BorderFactory.createEmptyBorder(
                        WindowUISettingsWindow.MARGIN,
                        0, 0, 0
                ));
                ticketsSettings.add(ticketTemplateLabel);
                ticketsSettings.add(new JLabel("Nastav?? cestu k souboru ??ablon?? j??zdenek, kter?? popisuje rozlo??en?? j??zdenek."));
                JPanel ticketTemplatePanel = new JPanel(new FlowLayout(
                        FlowLayout.LEADING
                ));
                
                File ticketTemplateFile = new File(this.config.ticketTemplate);
                this.ticketTemplate = new JTextField();
                this.ticketTemplate.setText(ticketTemplateFile.getAbsolutePath());
                ticketTemplatePanel.add(this.ticketTemplate);
                JButton ticketTemplateButton = new JButton();
                ticketTemplateButton.setIcon(new ImageIcon(WindowUI.UI_PATH + "/settings-search.png"));
                ticketTemplateButton.addActionListener((e) -> {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Vyberte ??ablonu j??zdenek");
                    chooser.setCurrentDirectory(ticketTemplateFile);
                    chooser.setAcceptAllFileFilterUsed(false);
                    chooser.addChoosableFileFilter(new FileNameExtensionFilter("??ablona j??zdenky (soubor XML)", "XML"));
                    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
                    {
                        File chosen = chooser.getSelectedFile();
                        this.ticketTemplate.setText(chosen.getAbsolutePath());
                    }
                });
                ticketTemplatePanel.add(ticketTemplateButton);
                ticketsSettings.add(ticketTemplatePanel);
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Background">
                JLabel ticketBackgroundLabel = new JLabel("Pozad?? j??zdenek");
                ticketBackgroundLabel.setFont(ITEM);
                ticketBackgroundLabel.setBorder(BorderFactory.createEmptyBorder(
                        WindowUISettingsWindow.MARGIN,
                        0, 0, 0
                ));
                ticketsSettings.add(ticketBackgroundLabel);
                ticketsSettings.add(new JLabel("Nastav?? cestu k souboru s obr??zkem, kter?? bude pou??it jako obr??zek na pozad?? ka??d?? j??zdenky."));
                JPanel ticketBackgroundPanel = new JPanel(new FlowLayout(
                        FlowLayout.LEADING
                ));
                
                File ticketBackgroundFile = new File(this.config.ticketBackground);
                this.ticketBackground = new JTextField();
                this.ticketBackground.setText(ticketBackgroundFile.getAbsolutePath());
                ticketBackgroundPanel.add(this.ticketBackground);
                JButton ticketBackgroundButton = new JButton();
                ticketBackgroundButton.setIcon(new ImageIcon(WindowUI.UI_PATH + "/settings-search.png"));
                ticketBackgroundButton.addActionListener((e) -> {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Vyberte ??ablonu j??zdenek");
                    chooser.setCurrentDirectory(ticketBackgroundFile);
                    chooser.setAcceptAllFileFilterUsed(false);
                    chooser.addChoosableFileFilter(new FileNameExtensionFilter("Obr??zek BMP", "BMP"));
                    chooser.addChoosableFileFilter(new FileNameExtensionFilter("Obr??zek GIF", "GIF"));
                    chooser.addChoosableFileFilter(new FileNameExtensionFilter("Obr??zek JPG", "JPG"));
                    chooser.addChoosableFileFilter(new FileNameExtensionFilter("Obr??zek JPEG", "JPEG"));
                    chooser.addChoosableFileFilter(new FileNameExtensionFilter("Obr??zek PNG", "PNG"));
                    chooser.addChoosableFileFilter(new FileNameExtensionFilter("Obr??zek TIF", "TIF"));
                    chooser.addChoosableFileFilter(new FileNameExtensionFilter("Obr??zek TIFF", "TIFF"));
                    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
                    {
                        File chosen = chooser.getSelectedFile();
                        this.ticketBackground.setText(chosen.getAbsolutePath());
                    }
                });
                ticketBackgroundPanel.add(ticketBackgroundButton);
                ticketsSettings.add(ticketBackgroundPanel);
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Issued">
                JLabel ticketIssuedLabel = new JLabel("Po????tadlo j??zdenek");
                ticketIssuedLabel.setFont(ITEM);
                ticketIssuedLabel.setBorder(BorderFactory.createEmptyBorder(
                        WindowUISettingsWindow.MARGIN,
                        0, 0, 0
                ));
                ticketsSettings.add(ticketIssuedLabel);
                ticketsSettings.add(new JLabel("Zobrazuje po??et ji?? vydan??ch j??zdenek."));
                JPanel ticketIssuedWarning = new JPanel(new FlowLayout(FlowLayout.LEADING));
                JLabel ticketIssuedWarningIcon = new JLabel();
                ticketIssuedWarningIcon.setIcon(new ImageIcon(WindowUI.UI_PATH +"/warning-s.png"));
                ticketIssuedWarning.add(ticketIssuedWarningIcon);
                JLabel ticketIssuedWarningText = new JLabel("<html>UPOZORN??N??: Zm??na tohoto ??daje m????e m??t nep??edv??dateln?? d??sledky.<br>Zm??nu prov??d??jte pouze pokud opravdu v??te, co d??l??te.</html>");
                ticketIssuedWarningText.setFont(super.getContentPane().getFont().deriveFont(Font.BOLD));
                ticketIssuedWarningText.setForeground(WindowUISettingsWindow.WARNING);
                ticketIssuedWarning.add(ticketIssuedWarningText);
                ticketsSettings.add(ticketIssuedWarning);
                JPanel ticketIssuedPanel = new JPanel(new FlowLayout(
                        FlowLayout.LEADING
                ));
                this.ticketIssued = new JSpinner(new SpinnerNumberModel(this.config.ticketNr, 0, Integer.MAX_VALUE, 1));
                ticketIssuedPanel.add(this.ticketIssued);
                ticketIssuedPanel.add(new JLabel("j??zdenek"));
                ticketsSettings.add(ticketIssuedPanel);
                //</editor-fold>
            for(Component c: ticketsSettings.getComponents())
            {
                JComponent j = (JComponent)c;
                j.setAlignmentX(Component.LEFT_ALIGNMENT);
                j.setAlignmentY(Component.TOP_ALIGNMENT);
            }
            //</editor-fold>
            content.add(ticketsSettings, "J??zdenky");
            //<editor-fold defaultstate="collapsed" desc="Appearance settings">
            JPanel appearanceSettings = new JPanel();
            appearanceSettings.setLayout(new BoxLayout(appearanceSettings, BoxLayout.Y_AXIS));
            appearanceSettings.setBorder(BorderFactory.createEmptyBorder(
                WindowUISettingsWindow.PADDING,
                WindowUISettingsWindow.PADDING,
                WindowUISettingsWindow.PADDING,
                WindowUISettingsWindow.PADDING));
                //<editor-fold defaultstate="collapsed" desc="Header">
                JPanel appearanceHeader = new JPanel(new FlowLayout(
                        FlowLayout.LEADING,
                        WindowUISettingsWindow.MARGIN,
                        WindowUISettingsWindow.MARGIN
                ));
                appearanceHeader.add(new JLabel(new ImageIcon(WindowUI.UI_PATH + "/settings-appearance.png")));
                JLabel appearanceHeaderLabel = new JLabel("Nastaven?? vzhledu");
                appearanceHeaderLabel.setFont(HEADER);
                appearanceHeader.add(appearanceHeaderLabel);
                appearanceSettings.add(appearanceHeader);
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Mode">
                JLabel appearanceModeLabel = new JLabel("Re??im aplikace");
                appearanceModeLabel.setFont(ITEM);
                appearanceModeLabel.setBorder(BorderFactory.createEmptyBorder(
                        WindowUISettingsWindow.MARGIN,
                        0, 0, 0
                ));
                appearanceSettings.add(appearanceModeLabel);
                appearanceSettings.add(new JLabel("Nastav?? re??im zobrazen?? aplikace."));
                ButtonGroup appearanceModeGroup = new ButtonGroup();
                this.appearanceModeGraphics = new JRadioButton("GRAFICK??", true);
                appearanceModeGroup.add(this.appearanceModeGraphics);
                appearanceSettings.add(this.appearanceModeGraphics);
                appearanceSettings.add(new JLabel("<html>Bude pou??ito ??pln?? grafick?? rozhran?? se v??emi prvky<br>jako jsou tla????tka, p??ep??na??e a podobn??.</html>"));
                appearanceSettings.add(new JLabel(System.lineSeparator()));
                this.appearanceModeText= new JRadioButton("TEXTOV??");
                appearanceModeGroup.add(this.appearanceModeText);
                appearanceSettings.add(this.appearanceModeText);
                appearanceSettings.add(new JLabel("<html>Bude pou??ito zjednodu??en?? grafick?? rozhran?? p??ipom??naj??c?? p????kazovou ????dku.<br>N??kter?? funkce aplikace mohou b??t omezen??.</html>"));
                appearanceSettings.add(new JLabel(System.lineSeparator()));
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Theme">
                JLabel appearanceThemeLabel = new JLabel("Motiv aplikace");
                appearanceThemeLabel.setFont(ITEM);
                appearanceThemeLabel.setBorder(BorderFactory.createEmptyBorder(
                        WindowUISettingsWindow.MARGIN,
                        0, 0, 0
                ));
                appearanceSettings.add(appearanceThemeLabel);
                appearanceSettings.add(new JLabel("Nastav?? motiv grafick??ho u??ivatelsk??ho rozhran??."));
                this.appearanceTheme = new JComboBox(this.themeMap.keySet().toArray());
                this.appearanceTheme.setSelectedItem(WindowUITheme.getTheme(Configuration.getInstance(jTicket.CONFIG_FILE).uiTheme).getDisplayName());
                appearanceSettings.add(this.appearanceTheme);
                //</editor-fold>
                for(int i = 0; i < 24; i++) // Fill remaining space
                {
                    appearanceSettings.add(new JLabel(System.lineSeparator()));
                }
            for(Component c: appearanceSettings.getComponents())
            {
                JComponent j = (JComponent)c;
                j.setAlignmentX(Component.LEFT_ALIGNMENT);
            }
            content.add(appearanceSettings, "Vzhled");
            //</editor-fold>
            content.add(new WindowUIAboutWindow(), "O programu");
            //<editor-fold defaultstate="collapsed" desc="Settings menu">
            String[] options = new String[]{"J??zdenky", "Vzhled", "O programu"};
            JList menu = new JList(options);
            menu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            Map<String, ImageIcon> imageMap = new HashMap<>();
            imageMap.put("J??zdenky", new ImageIcon(WindowUI.UI_PATH + "/settings-tickets.png"));
            imageMap.put("Vzhled", new ImageIcon(WindowUI.UI_PATH + "/settings-appearance.png"));
            imageMap.put("O programu", new ImageIcon(WindowUI.UI_PATH + "/about.png"));
            menu.setCellRenderer(new WindowUIImageListCellRenderer(imageMap));
            menu.setSelectedValue("J??zdenky", true);
            menu.setBorder(BorderFactory.createLoweredBevelBorder());
            menu.addListSelectionListener((e) -> {
                CardLayout cl = (CardLayout) content.getLayout();
                cl.show(content, (String)menu.getSelectedValue());
            });
            super.getContentPane().add(menu, BorderLayout.WEST);
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Buttons">
                JPanel buttonsPanel = new JPanel(new FlowLayout(
                        FlowLayout.TRAILING,
                        WindowUISettingsWindow.MARGIN,
                        WindowUISettingsWindow.MARGIN
                ));
                super.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
                JButton buttonCancel = new JButton("Zru??it");
                buttonCancel.addActionListener((e) -> {
                    this.dispose();
                });
                buttonsPanel.add(buttonCancel);
                JButton buttonSave = new JButton("Ulo??it zm??ny");
                buttonSave.addActionListener((e) -> {
                    this.config.ticketValidity = (Integer)this.ticketValidity.getValue();
                    this.config.outputDirectory = this.ticketOutput.getText();
                    this.config.VAT = (Double)this.ticketVAT.getValue();
                    this.config.ticketTemplate = this.ticketTemplate.getText();
                    this.config.ticketBackground = this.ticketBackground.getText();
                    this.config.ticketNr = (Integer)this.ticketIssued.getValue();
                    if (this.appearanceModeGraphics.isSelected())
                        this.config.uiMode = UIMode.GRAPHICS;
                    if (this.appearanceModeText.isSelected())
                        this.config.uiMode = UIMode.TEXT;
                    this.config.uiTheme = this.themeMap.get((String)this.appearanceTheme.getSelectedItem());
                    this.config.saveToFile();
                    WindowUIDialog dialog = new WindowUIDialog(
                            "Ulo??it nastaven??",
                            "Nastaven?? bylo ??sp????n?? ulo??eno." + System.lineSeparator() +
                            "N??kter?? zm??ny se projev?? a?? p??i op??tovn??m spu??t??n?? aplikace." + System.lineSeparator() + 
                            "Pokud je to mo??n??, ukon??ete program a znovu jej zapn??te.",
                            WindowUIDialogType.INFO,
                            WindowUIButtonType.OK
                    );
                    dialog.showDialog();
                    this.dispose();
                });
                buttonsPanel.add(buttonSave);
            //</editor-fold>
        //</editor-fold>
    }
}
