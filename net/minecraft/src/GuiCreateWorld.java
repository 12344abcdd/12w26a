package net.minecraft.src;

import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class GuiCreateWorld extends GuiScreen
{
    private GuiScreen parentGuiScreen;
    private GuiTextField textboxWorldName;
    private GuiTextField textboxSeed;
    private String folderName;

    /** hardcore', 'creative' or 'survival */
    private String gameMode;
    private boolean field_35365_g;
    private boolean field_55145_h;
    private boolean field_55146_i;
    private boolean field_55144_j;
    private boolean field_40232_h;
    private boolean createClicked;

    /**
     * True if the extra options (Seed box, structure toggle button, world type button, etc.) are being shown
     */
    private boolean moreOptions;

    /** The GUIButton that you click to change game modes. */
    private GuiButton gameModeButton;

    /**
     * The GUIButton that you click to get to options like the seed when creating a world.
     */
    private GuiButton moreWorldOptions;

    /** The GuiButton in the 'More World Options' screen. Toggles ON/OFF */
    private GuiButton generateStructuresButton;
    private GuiButton field_55148_x;

    /**
     * the GUIButton in the more world options screen. It's currently greyed out and unused in minecraft 1.0.0
     */
    private GuiButton worldTypeButton;
    private GuiButton field_55147_z;

    /** The first line of text describing the currently selected game mode. */
    private String gameModeDescriptionLine1;

    /** The second line of text describing the currently selected game mode. */
    private String gameModeDescriptionLine2;

    /** The current textboxSeed text */
    private String seed;

    /** E.g. New World, Neue Welt, Nieuwe wereld, Neuvo Mundo */
    private String localizedNewWorldText;
    private int field_46030_z;
    private static final String field_58064_F[] =
    {
        "CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4",
        "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5",
        "LPT6", "LPT7", "LPT8", "LPT9"
    };

    public GuiCreateWorld(GuiScreen par1GuiScreen)
    {
        gameMode = "survival";
        field_35365_g = true;
        field_55145_h = false;
        field_55146_i = false;
        field_55144_j = false;
        field_40232_h = false;
        field_46030_z = 0;
        parentGuiScreen = par1GuiScreen;
        seed = "";
        localizedNewWorldText = StatCollector.translateToLocal("selectWorld.newWorld");
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        textboxWorldName.updateCursorCounter();
        textboxSeed.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 155, height - 28, 150, 20, stringtranslate.translateKey("selectWorld.create")));
        controlList.add(new GuiButton(1, width / 2 + 5, height - 28, 150, 20, stringtranslate.translateKey("gui.cancel")));
        controlList.add(gameModeButton = new GuiButton(2, width / 2 - 75, 100, 150, 20, stringtranslate.translateKey("selectWorld.gameMode")));
        controlList.add(moreWorldOptions = new GuiButton(3, width / 2 - 75, 172, 150, 20, stringtranslate.translateKey("selectWorld.moreWorldOptions")));
        controlList.add(generateStructuresButton = new GuiButton(4, width / 2 - 155, 100, 150, 20, stringtranslate.translateKey("selectWorld.mapFeatures")));
        generateStructuresButton.drawButton = false;
        controlList.add(field_55148_x = new GuiButton(7, width / 2 + 5, 136, 150, 20, stringtranslate.translateKey("selectWorld.bonusItems")));
        field_55148_x.drawButton = false;
        controlList.add(worldTypeButton = new GuiButton(5, width / 2 + 5, 100, 150, 20, stringtranslate.translateKey("selectWorld.mapType")));
        worldTypeButton.drawButton = false;
        controlList.add(field_55147_z = new GuiButton(6, width / 2 - 155, 136, 150, 20, stringtranslate.translateKey("selectWorld.allowCommands")));
        field_55147_z.drawButton = false;
        textboxWorldName = new GuiTextField(fontRenderer, width / 2 - 100, 60, 200, 20);
        textboxWorldName.setFocused(true);
        textboxWorldName.setText(localizedNewWorldText);
        textboxSeed = new GuiTextField(fontRenderer, width / 2 - 100, 60, 200, 20);
        textboxSeed.setText(seed);
        makeUseableName();
        func_35363_g();
    }

    /**
     * Makes a the name for a world save folder based on your world name, replacing specific characters for _s and
     * appending -s to the end until a free name is available.
     */
    private void makeUseableName()
    {
        folderName = textboxWorldName.getText().trim();
        char ac[] = ChatAllowedCharacters.allowedCharactersArray;
        int i = ac.length;

        for (int j = 0; j < i; j++)
        {
            char c = ac[j];
            folderName = folderName.replace(c, '_');
        }

        if (MathHelper.stringNullOrLengthZero(folderName))
        {
            folderName = "World";
        }

        folderName = func_25097_a(mc.getSaveLoader(), folderName);
    }

    private void func_35363_g()
    {
        StringTranslate stringtranslate;
        stringtranslate = StringTranslate.getInstance();
        gameModeButton.displayString = (new StringBuilder()).append(stringtranslate.translateKey("selectWorld.gameMode")).append(" ").append(stringtranslate.translateKey((new StringBuilder()).append("selectWorld.gameMode.").append(gameMode).toString())).toString();
        gameModeDescriptionLine1 = stringtranslate.translateKey((new StringBuilder()).append("selectWorld.gameMode.").append(gameMode).append(".line1").toString());
        gameModeDescriptionLine2 = stringtranslate.translateKey((new StringBuilder()).append("selectWorld.gameMode.").append(gameMode).append(".line2").toString());
        generateStructuresButton.displayString = (new StringBuilder()).append(stringtranslate.translateKey("selectWorld.mapFeatures")).append(" ").toString();

        if (!(!field_35365_g))
        {
            generateStructuresButton.displayString += stringtranslate.translateKey("options.on");
        }
        else
        {
            generateStructuresButton.displayString += stringtranslate.translateKey("options.off");
        }

        field_55148_x.displayString = (new StringBuilder()).append(stringtranslate.translateKey("selectWorld.bonusItems")).append(" ").toString();

        if (!(!field_55144_j || field_40232_h))
        {
            field_55148_x.displayString += stringtranslate.translateKey("options.on");
        }
        else
        {
            field_55148_x.displayString += stringtranslate.translateKey("options.off");
        }

        worldTypeButton.displayString = (new StringBuilder()).append(stringtranslate.translateKey("selectWorld.mapType")).append(" ").append(stringtranslate.translateKey(WorldType.worldTypes[field_46030_z].getTranslateName())).toString();
        field_55147_z.displayString = (new StringBuilder()).append(stringtranslate.translateKey("selectWorld.allowCommands")).append(" ").toString();

        if (!(!field_55145_h || field_40232_h))
        {
            field_55147_z.displayString += stringtranslate.translateKey("options.on");
        }
        else
        {
            field_55147_z.displayString += stringtranslate.translateKey("options.off");
        }
    }

    public static String func_25097_a(ISaveFormat par0ISaveFormat, String par1Str)
    {
        par1Str = par1Str.replaceAll("[\\./\"]", "_");
        String as[] = field_58064_F;
        int i = as.length;

        for (int j = 0; j < i; j++)
        {
            String s = as[j];

            if (par1Str.equalsIgnoreCase(s))
            {
                par1Str = (new StringBuilder()).append("_").append(par1Str).append("_").toString();
            }
        }

        for (; par0ISaveFormat.getWorldInfo(par1Str) != null; par1Str = (new StringBuilder()).append(par1Str).append("-").toString()) { }

        return par1Str;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (!par1GuiButton.enabled)
        {
            return;
        }

        if (par1GuiButton.id == 1)
        {
            mc.displayGuiScreen(parentGuiScreen);
        }
        else if (par1GuiButton.id == 0)
        {
            mc.displayGuiScreen(null);

            if (createClicked)
            {
                return;
            }

            createClicked = true;
            long l = (new Random()).nextLong();
            String s = textboxSeed.getText();

            if (!MathHelper.stringNullOrLengthZero(s))
            {
                try
                {
                    long l1 = Long.parseLong(s);

                    if (l1 != 0L)
                    {
                        l = l1;
                    }
                }
                catch (NumberFormatException numberformatexception)
                {
                    l = s.hashCode();
                }
            }

            EnumGameType enumgametype = EnumGameType.func_57535_a(gameMode);
            WorldSettings worldsettings = new WorldSettings(l, enumgametype, field_35365_g, field_40232_h, WorldType.worldTypes[field_46030_z]);

            if (field_55144_j && !field_40232_h)
            {
                worldsettings.func_55250_a();
            }

            if (field_55145_h && !field_40232_h)
            {
                worldsettings.func_55248_b();
            }

            mc.func_58039_a(folderName, textboxWorldName.getText().trim(), worldsettings);
        }
        else if (par1GuiButton.id == 3)
        {
            moreOptions = !moreOptions;
            gameModeButton.drawButton = !moreOptions;
            generateStructuresButton.drawButton = moreOptions;
            field_55148_x.drawButton = moreOptions;
            worldTypeButton.drawButton = moreOptions;
            field_55147_z.drawButton = moreOptions;

            if (moreOptions)
            {
                StringTranslate stringtranslate = StringTranslate.getInstance();
                moreWorldOptions.displayString = stringtranslate.translateKey("gui.done");
            }
            else
            {
                StringTranslate stringtranslate1 = StringTranslate.getInstance();
                moreWorldOptions.displayString = stringtranslate1.translateKey("selectWorld.moreWorldOptions");
            }
        }
        else if (par1GuiButton.id == 2)
        {
            if (gameMode.equals("survival"))
            {
                if (!field_55146_i)
                {
                    field_55145_h = false;
                }

                field_40232_h = false;
                gameMode = "hardcore";
                field_40232_h = true;
                field_55147_z.enabled = false;
                field_55148_x.enabled = false;
                func_35363_g();
            }
            else if (gameMode.equals("hardcore"))
            {
                if (!field_55146_i)
                {
                    field_55145_h = true;
                }

                field_40232_h = false;
                gameMode = "creative";
                func_35363_g();
                field_40232_h = false;
                field_55147_z.enabled = true;
                field_55148_x.enabled = true;
            }
            else
            {
                if (!field_55146_i)
                {
                    field_55145_h = false;
                }

                gameMode = "survival";
                func_35363_g();
                field_55147_z.enabled = true;
                field_55148_x.enabled = true;
                field_40232_h = false;
            }

            func_35363_g();
        }
        else if (par1GuiButton.id == 4)
        {
            field_35365_g = !field_35365_g;
            func_35363_g();
        }
        else if (par1GuiButton.id == 7)
        {
            field_55144_j = !field_55144_j;
            func_35363_g();
        }
        else if (par1GuiButton.id == 5)
        {
            field_46030_z++;

            if (field_46030_z >= WorldType.worldTypes.length)
            {
                field_46030_z = 0;
            }

            do
            {
                if (WorldType.worldTypes[field_46030_z] != null && WorldType.worldTypes[field_46030_z].getCanBeCreated())
                {
                    break;
                }

                field_46030_z++;

                if (field_46030_z >= WorldType.worldTypes.length)
                {
                    field_46030_z = 0;
                }
            }
            while (true);

            func_35363_g();
        }
        else if (par1GuiButton.id == 6)
        {
            field_55146_i = true;
            field_55145_h = !field_55145_h;
            func_35363_g();
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (textboxWorldName.getIsFocused() && !moreOptions)
        {
            textboxWorldName.textboxKeyTyped(par1, par2);
            localizedNewWorldText = textboxWorldName.getText();
        }
        else if (textboxSeed.getIsFocused() && moreOptions)
        {
            textboxSeed.textboxKeyTyped(par1, par2);
            seed = textboxSeed.getText();
        }

        if (par1 == '\r')
        {
            actionPerformed((GuiButton)controlList.get(0));
        }

        ((GuiButton)controlList.get(0)).enabled = textboxWorldName.getText().length() > 0;
        makeUseableName();
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);

        if (moreOptions)
        {
            textboxSeed.mouseClicked(par1, par2, par3);
        }
        else
        {
            textboxWorldName.mouseClicked(par1, par2, par3);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawDefaultBackground();
        drawCenteredString(fontRenderer, stringtranslate.translateKey("selectWorld.create"), width / 2, 20, 0xffffff);

        if (moreOptions)
        {
            drawString(fontRenderer, stringtranslate.translateKey("selectWorld.enterSeed"), width / 2 - 100, 47, 0xa0a0a0);
            drawString(fontRenderer, stringtranslate.translateKey("selectWorld.seedInfo"), width / 2 - 100, 85, 0xa0a0a0);
            drawString(fontRenderer, stringtranslate.translateKey("selectWorld.mapFeatures.info"), width / 2 - 150, 122, 0xa0a0a0);
            drawString(fontRenderer, stringtranslate.translateKey("selectWorld.allowCommands.info"), width / 2 - 150, 157, 0xa0a0a0);
            textboxSeed.drawTextBox();
        }
        else
        {
            drawString(fontRenderer, stringtranslate.translateKey("selectWorld.enterName"), width / 2 - 100, 47, 0xa0a0a0);
            drawString(fontRenderer, (new StringBuilder()).append(stringtranslate.translateKey("selectWorld.resultFolder")).append(" ").append(folderName).toString(), width / 2 - 100, 85, 0xa0a0a0);
            textboxWorldName.drawTextBox();
            drawString(fontRenderer, gameModeDescriptionLine1, width / 2 - 100, 122, 0xa0a0a0);
            drawString(fontRenderer, gameModeDescriptionLine2, width / 2 - 100, 134, 0xa0a0a0);
        }

        super.drawScreen(par1, par2, par3);
    }
}
