package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiShareToLan extends GuiScreen
{
    private final GuiScreen field_58063_a;
    private GuiButton field_58061_b;
    private GuiButton field_58062_c;
    private String field_58059_d;
    private boolean field_58060_e;

    public GuiShareToLan(GuiScreen par1GuiScreen)
    {
        field_58059_d = "survival";
        field_58060_e = false;
        field_58063_a = par1GuiScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        controlList.clear();
        controlList.add(new GuiButton(101, width / 2 - 155, height - 28, 150, 20, StatCollector.translateToLocal("lanServer.start")));
        controlList.add(new GuiButton(102, width / 2 + 5, height - 28, 150, 20, StatCollector.translateToLocal("gui.cancel")));
        controlList.add(field_58062_c = new GuiButton(104, width / 2 - 155, 100, 150, 20, StatCollector.translateToLocal("selectWorld.gameMode")));
        controlList.add(field_58061_b = new GuiButton(103, width / 2 + 5, 100, 150, 20, StatCollector.translateToLocal("selectWorld.allowCommands")));
        func_58058_g();
    }

    private void func_58058_g()
    {
        StringTranslate stringtranslate;
        stringtranslate = StringTranslate.getInstance();
        field_58062_c.displayString = (new StringBuilder()).append(stringtranslate.translateKey("selectWorld.gameMode")).append(" ").append(stringtranslate.translateKey((new StringBuilder()).append("selectWorld.gameMode.").append(field_58059_d).toString())).toString();
        field_58061_b.displayString = (new StringBuilder()).append(stringtranslate.translateKey("selectWorld.allowCommands")).append(" ").toString();

        if (!(!field_58060_e))
        {
            field_58061_b.displayString += stringtranslate.translateKey("options.on");
        }
        else
        {
            field_58061_b.displayString += stringtranslate.translateKey("options.off");
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 102)
        {
            mc.displayGuiScreen(field_58063_a);
        }
        else if (par1GuiButton.id == 104)
        {
            if (field_58059_d.equals("survival"))
            {
                field_58059_d = "creative";
            }
            else if (field_58059_d.equals("creative"))
            {
                field_58059_d = "adventure";
            }
            else
            {
                field_58059_d = "survival";
            }

            func_58058_g();
        }
        else if (par1GuiButton.id == 103)
        {
            field_58060_e = !field_58060_e;
            func_58058_g();
        }
        else if (par1GuiButton.id == 101)
        {
            mc.displayGuiScreen(null);
            String s = mc.func_58040_C().func_58032_a(EnumGameType.func_57535_a(field_58059_d), field_58060_e);
            String s1 = "";

            if (s != null)
            {
                s1 = mc.field_56455_h.func_55085_a("commands.publish.started", new Object[]
                        {
                            s
                        });
            }
            else
            {
                s1 = mc.field_56455_h.func_55085_a("commands.publish.failed", new Object[0]);
            }

            mc.ingameGUI.func_55092_b().func_55106_a(s1);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, StatCollector.translateToLocal("lanServer.title"), width / 2, 50, 0xffffff);
        drawCenteredString(fontRenderer, StatCollector.translateToLocal("lanServer.otherPlayers"), width / 2, 82, 0xffffff);
        super.drawScreen(par1, par2, par3);
    }
}
