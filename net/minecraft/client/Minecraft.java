package net.minecraft.client;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import net.minecraft.src.AABBPool;
import net.minecraft.src.Achievement;
import net.minecraft.src.AchievementList;
import net.minecraft.src.AnvilSaveConverter;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.CallableGLInfo;
import net.minecraft.src.CallableLWJGLVersion;
import net.minecraft.src.CallableModded;
import net.minecraft.src.ColorizerFoliage;
import net.minecraft.src.ColorizerGrass;
import net.minecraft.src.ColorizerWater;
import net.minecraft.src.Container;
import net.minecraft.src.CrashReport;
import net.minecraft.src.EffectRenderer;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EntityRenderer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.EnumOS;
import net.minecraft.src.EnumOSHelper;
import net.minecraft.src.EnumOptions;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GameWindowListener;
import net.minecraft.src.GuiAchievement;
import net.minecraft.src.GuiChat;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiErrorScreen;
import net.minecraft.src.GuiGameOver;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.GuiIngameMenu;
import net.minecraft.src.GuiInventory;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiMemoryErrorScreen;
import net.minecraft.src.GuiNewChat;
import net.minecraft.src.GuiParticle;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSleepMP;
import net.minecraft.src.IPlayerUsage;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.IntegratedServer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.LoadingScreenRenderer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MemoryConnection;
import net.minecraft.src.MinecraftError;
import net.minecraft.src.MinecraftFakeLauncher;
import net.minecraft.src.MouseHelper;
import net.minecraft.src.MovementInputFromOptions;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.OpenGlHelper;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.PlayerCapabilities;
import net.minecraft.src.PlayerControllerMP;
import net.minecraft.src.PlayerUsageSnooper;
import net.minecraft.src.Profiler;
import net.minecraft.src.ProfilerResult;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderManager;
import net.minecraft.src.ReportedException;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.ScreenShotHelper;
import net.minecraft.src.ServerData;
import net.minecraft.src.ServerLauncher;
import net.minecraft.src.Session;
import net.minecraft.src.SoundManager;
import net.minecraft.src.StatCollector;
import net.minecraft.src.StatFileWriter;
import net.minecraft.src.StatList;
import net.minecraft.src.StatStringFormatKeyInv;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TextureCompassFX;
import net.minecraft.src.TextureFlamesFX;
import net.minecraft.src.TextureLavaFX;
import net.minecraft.src.TextureLavaFlowFX;
import net.minecraft.src.TexturePackBase;
import net.minecraft.src.TexturePackList;
import net.minecraft.src.TexturePortalFX;
import net.minecraft.src.TextureWatchFX;
import net.minecraft.src.TextureWaterFX;
import net.minecraft.src.TextureWaterFlowFX;
import net.minecraft.src.ThreadClientSleep;
import net.minecraft.src.ThreadDownloadResources;
import net.minecraft.src.Timer;
import net.minecraft.src.Vec3;
import net.minecraft.src.Vec3Pool;
import net.minecraft.src.WorldClient;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldRenderer;
import net.minecraft.src.WorldSettings;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public abstract class Minecraft implements IPlayerUsage, Runnable
{
    public static byte field_28006_b[] = new byte[0xa00000];
    private ServerData field_56454_a;

    /**
     * Set to 'this' in Minecraft constructor; used by some settings get methods
     */
    private static Minecraft theMinecraft;
    public PlayerControllerMP field_56453_c;
    private boolean fullscreen;
    private boolean hasCrashed;
    private CrashReport field_56450_U;
    public int displayWidth;
    public int displayHeight;
    private Timer timer;
    private PlayerUsageSnooper field_56451_W;
    public WorldClient field_56452_f;
    public RenderGlobal renderGlobal;
    public EntityClientPlayerMP field_56455_h;

    /**
     * The Entity from which the renderer determines the render viewpoint. Currently is always the parent Minecraft
     * class's 'thePlayer' instance. Modification of its location, rotation, or other settings at render time will
     * modify the camera likewise, with the caveat of triggering chunk rebuilds as it moves, making it unsuitable for
     * changing the viewpoint mid-render.
     */
    public EntityLiving renderViewEntity;
    public EffectRenderer effectRenderer;
    public Session session;
    public String minecraftUri;
    public Canvas mcCanvas;

    /** a boolean to hide a Quit button from the main menu */
    public boolean hideQuitButton;
    public volatile boolean isGamePaused;

    /** The RenderEngine instance used by Minecraft */
    public RenderEngine renderEngine;

    /** The font renderer used for displaying and measuring text. */
    public FontRenderer fontRenderer;
    public FontRenderer standardGalacticFontRenderer;

    /** The GuiScreen that's being displayed at the moment. */
    public GuiScreen currentScreen;
    public LoadingScreenRenderer loadingScreen;
    public EntityRenderer entityRenderer;

    /** Reference to the download resources thread. */
    private ThreadDownloadResources downloadResourcesThread;

    /** Mouse left click counter */
    private int leftClickCounter;

    /** Display width */
    private int tempDisplayWidth;

    /** Display height */
    private int tempDisplayHeight;
    private final ServerLauncher field_56459_ab = new ServerLauncher();
    private IntegratedServer field_56458_ac;

    /** Gui achievement */
    public GuiAchievement guiAchievement;
    public GuiIngame ingameGUI;

    /** Skip render world */
    public boolean skipRenderWorld;

    /** The ray trace hit that the mouse is over. */
    public MovingObjectPosition objectMouseOver;

    /** The game settings that currently hold effect. */
    public GameSettings gameSettings;
    protected MinecraftApplet mcApplet;
    public SoundManager sndManager;

    /** Mouse helper instance. */
    public MouseHelper mouseHelper;

    /** The TexturePackLister used by this instance of Minecraft... */
    public TexturePackList texturePackList;
    public File mcDataDir;
    private ISaveFormat saveLoader;
    public static long frameTimes[] = new long[512];
    public static long tickTimes[] = new long[512];
    public static int numRecordedFrameTimes = 0;
    private static int field_56461_ae;

    /**
     * When you place a block, it's set to 6, decremented once per tick, when it's 0, you can place another block.
     */
    private int rightClickDelayTimer;
    private boolean field_56460_ag;

    /** Stat file writer */
    public StatFileWriter statFileWriter;
    private String serverName;
    private int serverPort;
    private TextureWaterFX textureWaterFX;
    private TextureLavaFX textureLavaFX;

    /**
     * Makes sure it doesn't keep taking screenshots when both buttons are down.
     */
    boolean isTakingScreenshot;

    /**
     * Does the actual gameplay have focus. If so then mouse and keys will effect the player instead of menus.
     */
    public boolean inGameHasFocus;
    long systemTime;

    /** Join player counter */
    private int joinPlayerCounter;
    private boolean field_55073_an;
    private NetworkManager field_56457_an;
    private boolean field_56456_ao;

    /** The working dir (OS specific) for minecraft */
    private static File minecraftDir = null;

    /**
     * Set to true to keep the game loop running. Set to false by shutdown() to allow the game loop to exit cleanly.
     */
    public volatile boolean running;

    /** String that shows the debug information */
    public String debug;

    /** Approximate time (in ms) of last update to debug string */
    long debugUpdateTime;

    /** holds the current fps */
    int fpsCounter;
    long prevFrameTime;

    /** Profiler currently displayed in the debug screen pie chart */
    private String debugProfilerName;

    public Minecraft(Canvas par1Canvas, MinecraftApplet par2MinecraftApplet, int par3, int par4, boolean par5)
    {
        fullscreen = false;
        hasCrashed = false;
        timer = new Timer(20F);
        field_56451_W = new PlayerUsageSnooper("client", this);
        session = null;
        hideQuitButton = false;
        isGamePaused = false;
        currentScreen = null;
        leftClickCounter = 0;
        guiAchievement = new GuiAchievement(this);
        skipRenderWorld = false;
        objectMouseOver = null;
        sndManager = new SoundManager();
        rightClickDelayTimer = 0;
        textureWaterFX = new TextureWaterFX();
        textureLavaFX = new TextureLavaFX();
        isTakingScreenshot = false;
        inGameHasFocus = false;
        systemTime = System.currentTimeMillis();
        joinPlayerCounter = 0;
        running = true;
        debug = "";
        debugUpdateTime = System.currentTimeMillis();
        fpsCounter = 0;
        prevFrameTime = -1L;
        debugProfilerName = "root";
        StatList.func_27360_a();
        tempDisplayHeight = par4;
        fullscreen = par5;
        mcApplet = par2MinecraftApplet;
        Packet3Chat.field_52010_b = 32767;
        func_56440_C();
        mcCanvas = par1Canvas;
        displayWidth = par3;
        displayHeight = par4;
        fullscreen = par5;
        theMinecraft = this;
    }

    private void func_56440_C()
    {
        ThreadClientSleep threadclientsleep = new ThreadClientSleep(this, "Timer hack thread");
        threadclientsleep.setDaemon(true);
        threadclientsleep.start();
    }

    public void func_56448_b(CrashReport par1CrashReport)
    {
        hasCrashed = true;
        field_56450_U = par1CrashReport;
    }

    public void func_55071_b(CrashReport par1CrashReport)
    {
        hasCrashed = true;
        func_55070_a(par1CrashReport);
    }

    public abstract void func_55070_a(CrashReport crashreport);

    public void setServer(String par1Str, int par2)
    {
        serverName = par1Str;
        serverPort = par2;
    }

    /**
     * Starts the game: initializes the canvas, the title, the settings, etcetera.
     */
    public void startGame() throws LWJGLException
    {
        if (mcCanvas != null)
        {
            Graphics g = mcCanvas.getGraphics();

            if (g != null)
            {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, displayWidth, displayHeight);
                g.dispose();
            }

            Display.setParent(mcCanvas);
        }
        else if (fullscreen)
        {
            Display.setFullscreen(true);
            displayWidth = Display.getDisplayMode().getWidth();
            displayHeight = Display.getDisplayMode().getHeight();

            if (displayWidth <= 0)
            {
                displayWidth = 1;
            }

            if (displayHeight <= 0)
            {
                displayHeight = 1;
            }
        }
        else
        {
            Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
        }

        Display.setTitle("Minecraft Minecraft 12w26a");
        System.out.println((new StringBuilder()).append("LWJGL Version: ").append(Sys.getVersion()).toString());

        try
        {
            Display.create((new PixelFormat()).withDepthBits(24));
        }
        catch (LWJGLException lwjglexception)
        {
            lwjglexception.printStackTrace();

            try
            {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedexception) { }

            Display.create();
        }

        OpenGlHelper.initializeTextures();
        mcDataDir = getMinecraftDir();
        saveLoader = new AnvilSaveConverter(new File(mcDataDir, "saves"));
        gameSettings = new GameSettings(this, mcDataDir);
        texturePackList = new TexturePackList(mcDataDir, this);
        renderEngine = new RenderEngine(texturePackList, gameSettings);
        loadScreen();
        fontRenderer = new FontRenderer(gameSettings, "/font/default.png", renderEngine, false);
        standardGalacticFontRenderer = new FontRenderer(gameSettings, "/font/alternate.png", renderEngine, false);

        if (gameSettings.language != null)
        {
            StringTranslate.getInstance().setLanguage(gameSettings.language);
            fontRenderer.setUnicodeFlag(StringTranslate.getInstance().isUnicode());
            fontRenderer.setBidiFlag(StringTranslate.isBidrectional(gameSettings.language));
        }

        ColorizerWater.setWaterBiomeColorizer(renderEngine.getTextureContents("/misc/watercolor.png"));
        ColorizerGrass.setGrassBiomeColorizer(renderEngine.getTextureContents("/misc/grasscolor.png"));
        ColorizerFoliage.getFoilageBiomeColorizer(renderEngine.getTextureContents("/misc/foliagecolor.png"));
        entityRenderer = new EntityRenderer(this);
        RenderManager.instance.itemRenderer = new ItemRenderer(this);
        statFileWriter = new StatFileWriter(session, mcDataDir);
        AchievementList.openInventory.setStatStringFormatter(new StatStringFormatKeyInv(this));
        loadScreen();
        Mouse.create();
        mouseHelper = new MouseHelper(mcCanvas);
        checkGLError("Pre startup");
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearDepth(1.0D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        checkGLError("Startup");
        sndManager.loadSoundSettings(gameSettings);
        renderEngine.registerTextureFX(textureLavaFX);
        renderEngine.registerTextureFX(textureWaterFX);
        renderEngine.registerTextureFX(new TexturePortalFX());
        renderEngine.registerTextureFX(new TextureCompassFX(this));
        renderEngine.registerTextureFX(new TextureWatchFX(this));
        renderEngine.registerTextureFX(new TextureWaterFlowFX());
        renderEngine.registerTextureFX(new TextureLavaFlowFX());
        renderEngine.registerTextureFX(new TextureFlamesFX(0));
        renderEngine.registerTextureFX(new TextureFlamesFX(1));
        renderGlobal = new RenderGlobal(this, renderEngine);
        GL11.glViewport(0, 0, displayWidth, displayHeight);
        effectRenderer = new EffectRenderer(field_56452_f, renderEngine);

        try
        {
            downloadResourcesThread = new ThreadDownloadResources(mcDataDir, this);
            downloadResourcesThread.start();
        }
        catch (Exception exception) { }

        checkGLError("Post startup");
        ingameGUI = new GuiIngame(this);

        if (serverName != null)
        {
            displayGuiScreen(new GuiConnecting(this, serverName, serverPort));
        }
        else
        {
            displayGuiScreen(new GuiMainMenu());
        }

        loadingScreen = new LoadingScreenRenderer(this);
    }

    /**
     * Displays a new screen.
     */
    private void loadScreen() throws LWJGLException
    {
        ScaledResolution scaledresolution = new ScaledResolution(gameSettings, displayWidth, displayHeight);
        GL11.glClear(16640);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, scaledresolution.func_57188_c(), scaledresolution.func_57186_d(), 0.0D, 1000D, 3000D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000F);
        GL11.glViewport(0, 0, displayWidth, displayHeight);
        GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_FOG);
        Tessellator tessellator = Tessellator.instance;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderEngine.getTexture("/title/mojang.png"));
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_I(0xffffff);
        tessellator.addVertexWithUV(0.0D, displayHeight, 0.0D, 0.0D, 0.0D);
        tessellator.addVertexWithUV(displayWidth, displayHeight, 0.0D, 0.0D, 0.0D);
        tessellator.addVertexWithUV(displayWidth, 0.0D, 0.0D, 0.0D, 0.0D);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        tessellator.draw();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        tessellator.setColorOpaque_I(0xffffff);
        char c = 256;
        char c1 = 256;
        scaledTessellator((scaledresolution.getScaledWidth() - c) / 2, (scaledresolution.getScaledHeight() - c1) / 2, 0, 0, c, c1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        Display.swapBuffers();
    }

    /**
     * Loads Tessellator with a scaled resolution
     */
    public void scaledTessellator(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(par1 + 0, par2 + par6, 0.0D, (float)(par3 + 0) * f, (float)(par4 + par6) * f1);
        tessellator.addVertexWithUV(par1 + par5, par2 + par6, 0.0D, (float)(par3 + par5) * f, (float)(par4 + par6) * f1);
        tessellator.addVertexWithUV(par1 + par5, par2 + 0, 0.0D, (float)(par3 + par5) * f, (float)(par4 + 0) * f1);
        tessellator.addVertexWithUV(par1 + 0, par2 + 0, 0.0D, (float)(par3 + 0) * f, (float)(par4 + 0) * f1);
        tessellator.draw();
    }

    /**
     * gets the working dir (OS specific) for minecraft
     */
    public static File getMinecraftDir()
    {
        if (minecraftDir == null)
        {
            minecraftDir = getAppDir("minecraft");
        }

        return minecraftDir;
    }

    /**
     * gets the working dir (OS specific) for the specific application (which is always minecraft)
     */
    public static File getAppDir(String par0Str)
    {
        String s = System.getProperty("user.home", ".");
        File file;

        switch (EnumOSHelper.field_58131_a[getOs().ordinal()])
        {
            case 1:
            case 2:
                file = new File(s, (new StringBuilder()).append('.').append(par0Str).append('/').toString());
                break;

            case 3:
                String s1 = System.getenv("APPDATA");

                if (s1 != null)
                {
                    file = new File(s1, (new StringBuilder()).append(".").append(par0Str).append('/').toString());
                }
                else
                {
                    file = new File(s, (new StringBuilder()).append('.').append(par0Str).append('/').toString());
                }

                break;

            case 4:
                file = new File(s, (new StringBuilder()).append("Library/Application Support/").append(par0Str).toString());
                break;

            default:
                file = new File(s, (new StringBuilder()).append(par0Str).append('/').toString());
                break;
        }

        if (!file.exists() && !file.mkdirs())
        {
            throw new RuntimeException((new StringBuilder()).append("The working directory could not be created: ").append(file).toString());
        }
        else
        {
            return file;
        }
    }

    public static EnumOS getOs()
    {
        String s = System.getProperty("os.name").toLowerCase();

        if (s.contains("win"))
        {
            return EnumOS.WINDOWS;
        }

        if (s.contains("mac"))
        {
            return EnumOS.MACOS;
        }

        if (s.contains("solaris"))
        {
            return EnumOS.SOLARIS;
        }

        if (s.contains("sunos"))
        {
            return EnumOS.SOLARIS;
        }

        if (s.contains("linux"))
        {
            return EnumOS.LINUX;
        }

        if (s.contains("unix"))
        {
            return EnumOS.LINUX;
        }
        else
        {
            return EnumOS.UNKNOWN;
        }
    }

    /**
     * Returns the save loader that is currently being used
     */
    public ISaveFormat getSaveLoader()
    {
        return saveLoader;
    }

    /**
     * Sets the argument GuiScreen as the main (topmost visible) screen.
     */
    public void displayGuiScreen(GuiScreen par1GuiScreen)
    {
        if (currentScreen instanceof GuiErrorScreen)
        {
            return;
        }

        if (currentScreen != null)
        {
            currentScreen.onGuiClosed();
        }

        statFileWriter.syncStats();

        if (par1GuiScreen == null && field_56452_f == null)
        {
            par1GuiScreen = new GuiMainMenu();
        }
        else if (par1GuiScreen == null && field_56455_h.getHealth() <= 0)
        {
            par1GuiScreen = new GuiGameOver();
        }

        if (par1GuiScreen instanceof GuiMainMenu)
        {
            gameSettings.showDebugInfo = false;
            ingameGUI.func_55092_b().func_55101_a();
        }

        currentScreen = par1GuiScreen;

        if (par1GuiScreen != null)
        {
            setIngameNotInFocus();
            ScaledResolution scaledresolution = new ScaledResolution(gameSettings, displayWidth, displayHeight);
            int i = scaledresolution.getScaledWidth();
            int j = scaledresolution.getScaledHeight();
            par1GuiScreen.setWorldAndResolution(this, i, j);
            skipRenderWorld = false;
        }
        else
        {
            setIngameFocus();
        }
    }

    /**
     * Checks for an OpenGL error. If there is one, prints the error ID and error string.
     */
    private void checkGLError(String par1Str)
    {
        int i = GL11.glGetError();

        if (i != 0)
        {
            String s = GLU.gluErrorString(i);
            System.out.println("########## GL ERROR ##########");
            System.out.println((new StringBuilder()).append("@ ").append(par1Str).toString());
            System.out.println((new StringBuilder()).append(i).append(": ").append(s).toString());
        }
    }

    /**
     * Shuts down the minecraft applet by stopping the resource downloads, and clearing up GL stuff; called when the
     * application (or web page) is exited.
     */
    public void shutdownMinecraftApplet()
    {
        try
        {
            statFileWriter.syncStats();

            try
            {
                if (downloadResourcesThread != null)
                {
                    downloadResourcesThread.closeMinecraft();
                }
            }
            catch (Exception exception) { }

            System.out.println("Stopping!");

            try
            {
                func_56445_a(null);
            }
            catch (Throwable throwable) { }

            try
            {
                GLAllocation.deleteTexturesAndDisplayLists();
            }
            catch (Throwable throwable1) { }

            sndManager.closeMinecraft();
            Mouse.destroy();
            Keyboard.destroy();
        }
        finally
        {
            Display.destroy();

            if (!hasCrashed)
            {
                System.exit(0);
            }
        }

        System.gc();
    }

    public void run()
    {
        running = true;

        try
        {
            startGame();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            func_55071_b(func_55067_c(new CrashReport("Failed to start game", exception)));
            return;
        }

        try
        {
            while (running)
            {
                if (hasCrashed && field_56450_U != null)
                {
                    func_55071_b(field_56450_U);
                    return;
                }

                if (field_56460_ag)
                {
                    field_56460_ag = false;
                    renderEngine.refreshTextures();
                }

                try
                {
                    runGameLoop();
                }
                catch (OutOfMemoryError outofmemoryerror)
                {
                    freeMemory();
                    displayGuiScreen(new GuiMemoryErrorScreen());
                    System.gc();
                }
            }
        }
        catch (MinecraftError minecrafterror) { }
        catch (ReportedException reportedexception)
        {
            freeMemory();
            reportedexception.printStackTrace();
            func_55071_b(reportedexception.func_57397_a());
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = func_55067_c(new CrashReport("Unexpected error", throwable));
            freeMemory();
            throwable.printStackTrace();
            func_55071_b(crashreport);
        }
        finally
        {
            shutdownMinecraftApplet();
        }
    }

    /**
     * Called repeatedly from run()
     */
    private void runGameLoop()
    {
        if (mcApplet != null && !mcApplet.isActive())
        {
            running = false;
            return;
        }

        AxisAlignedBB.func_58144_a().func_58151_a();
        Vec3.func_58128_a().func_58135_a();
        Profiler.startSection("root");

        if (mcCanvas == null && Display.isCloseRequested())
        {
            shutdown();
        }

        if (isGamePaused && field_56452_f != null)
        {
            float f = timer.renderPartialTicks;
            timer.updateTimer();
            timer.renderPartialTicks = f;
        }
        else
        {
            timer.updateTimer();
        }

        long l = System.nanoTime();
        Profiler.startSection("tick");

        for (int i = 0; i < timer.elapsedTicks; i++)
        {
            runTick();
        }

        Profiler.endSection();
        long l1 = System.nanoTime() - l;
        checkGLError("Pre render");
        RenderBlocks.fancyGrass = gameSettings.fancyGraphics;
        Profiler.startSection("sound");
        sndManager.setListener(field_56455_h, timer.renderPartialTicks);
        Profiler.endStartSection("updatelights");

        if (field_56452_f != null)
        {
            field_56452_f.updatingLighting();
        }

        Profiler.endSection();
        Profiler.startSection("render");
        Profiler.startSection("display");
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        if (!Keyboard.isKeyDown(65))
        {
            Display.update();
        }

        if (field_56455_h != null && field_56455_h.isEntityInsideOpaqueBlock())
        {
            gameSettings.thirdPersonView = 0;
        }

        Profiler.endSection();

        if (!skipRenderWorld)
        {
            Profiler.endStartSection("gameRenderer");
            entityRenderer.updateCameraAndRender(timer.renderPartialTicks);
            Profiler.endSection();
        }

        GL11.glFlush();
        Profiler.endSection();

        if (!Display.isActive() && fullscreen)
        {
            toggleFullscreen();
        }

        Profiler.endSection();

        if (gameSettings.showDebugInfo && gameSettings.field_50119_G)
        {
            if (!Profiler.profilingEnabled)
            {
                Profiler.clearProfiling();
            }

            Profiler.profilingEnabled = false;
            displayDebugInfo(l1);
        }
        else
        {
            Profiler.profilingEnabled = false;
            prevFrameTime = System.nanoTime();
        }

        guiAchievement.updateAchievementWindow();
        Profiler.startSection("root");
        Thread.yield();

        if (Keyboard.isKeyDown(65))
        {
            Display.update();
        }

        screenshotListener();

        if (mcCanvas != null && !fullscreen && (mcCanvas.getWidth() != displayWidth || mcCanvas.getHeight() != displayHeight))
        {
            displayWidth = mcCanvas.getWidth();
            displayHeight = mcCanvas.getHeight();

            if (displayWidth <= 0)
            {
                displayWidth = 1;
            }

            if (displayHeight <= 0)
            {
                displayHeight = 1;
            }

            resize(displayWidth, displayHeight);
        }

        checkGLError("Post render");
        fpsCounter++;
        boolean flag = isGamePaused;
        isGamePaused = func_56439_B() && currentScreen != null && currentScreen.doesGuiPauseGame() && !field_56458_ac.func_56415_af();

        if (func_56444_A() && field_56455_h != null && field_56455_h.sendQueue != null && isGamePaused != flag)
        {
            ((MemoryConnection)field_56455_h.sendQueue.func_56761_e()).func_57322_a(isGamePaused);
        }

        do
        {
            if (System.currentTimeMillis() < debugUpdateTime + 1000L)
            {
                break;
            }

            field_56461_ae = fpsCounter;
            debug = (new StringBuilder()).append(field_56461_ae).append(" fps, ").append(WorldRenderer.chunksUpdated).append(" chunk updates").toString();
            WorldRenderer.chunksUpdated = 0;
            debugUpdateTime += 1000L;
            fpsCounter = 0;
            field_56451_W.func_57207_b();

            if (!field_56451_W.func_57210_c())
            {
                field_56451_W.func_57205_a();
            }
        }
        while (true);

        Profiler.endSection();
    }

    public void freeMemory()
    {
        try
        {
            field_28006_b = new byte[0];
            renderGlobal.func_28137_f();
        }
        catch (Throwable throwable) { }

        try
        {
            System.gc();
            AxisAlignedBB.func_58144_a().func_58152_b();
            Vec3.func_58128_a().func_58136_b();
        }
        catch (Throwable throwable1) { }

        try
        {
            System.gc();
            func_56445_a(null);
        }
        catch (Throwable throwable2) { }

        System.gc();
    }

    /**
     * checks if keys are down
     */
    private void screenshotListener()
    {
        if (Keyboard.isKeyDown(60))
        {
            if (!isTakingScreenshot)
            {
                isTakingScreenshot = true;
                ingameGUI.func_55092_b().func_55106_a(ScreenShotHelper.saveScreenshot(minecraftDir, displayWidth, displayHeight));
            }
        }
        else
        {
            isTakingScreenshot = false;
        }
    }

    /**
     * Update debugProfilerName in response to number keys in debug screen
     */
    private void updateDebugProfilerName(int par1)
    {
        java.util.List list;
        ProfilerResult profilerresult;
        list = Profiler.getProfilingData(debugProfilerName);

        if (list == null || list.isEmpty())
        {
            return;
        }

        profilerresult = (ProfilerResult)list.remove(0);

        if (!(par1 != 0))
        {
            if (profilerresult.name.length() > 0)
            {
                int i = debugProfilerName.lastIndexOf(".");

                if (i >= 0)
                {
                    debugProfilerName = debugProfilerName.substring(0, i);
                }
            }
        }
        else
        {
            if (!(--par1 >= list.size() || ((ProfilerResult)list.get(par1)).name.equals("unspecified")))
            {
                if (!(debugProfilerName.length() <= 0))
                {
                    debugProfilerName += ".";
                }

                debugProfilerName += ((ProfilerResult)list.get(par1)).name;
            }
        }
    }

    private void displayDebugInfo(long par1)
    {
        if (!Profiler.profilingEnabled)
        {
            return;
        }

        java.util.List list = Profiler.getProfilingData(debugProfilerName);
        ProfilerResult profilerresult = (ProfilerResult)list.remove(0);
        long l = 0xfe502aL;

        if (prevFrameTime == -1L)
        {
            prevFrameTime = System.nanoTime();
        }

        long l1 = System.nanoTime();
        tickTimes[numRecordedFrameTimes & frameTimes.length - 1] = par1;
        frameTimes[numRecordedFrameTimes++ & frameTimes.length - 1] = l1 - prevFrameTime;
        prevFrameTime = l1;
        GL11.glClear(256);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, displayWidth, displayHeight, 0.0D, 1000D, 3000D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000F);
        GL11.glLineWidth(1.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(7);
        int i = (int)(l / 0x30d40L);
        tessellator.setColorOpaque_I(0x20000000);
        tessellator.addVertex(0.0D, displayHeight - i, 0.0D);
        tessellator.addVertex(0.0D, displayHeight, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight - i, 0.0D);
        tessellator.setColorOpaque_I(0x20200000);
        tessellator.addVertex(0.0D, displayHeight - i * 2, 0.0D);
        tessellator.addVertex(0.0D, displayHeight - i, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight - i, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight - i * 2, 0.0D);
        tessellator.draw();
        long l2 = 0L;

        for (int j = 0; j < frameTimes.length; j++)
        {
            l2 += frameTimes[j];
        }

        int k = (int)(l2 / 0x30d40L / (long)frameTimes.length);
        tessellator.startDrawing(7);
        tessellator.setColorOpaque_I(0x20400000);
        tessellator.addVertex(0.0D, displayHeight - k, 0.0D);
        tessellator.addVertex(0.0D, displayHeight, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight - k, 0.0D);
        tessellator.draw();
        tessellator.startDrawing(1);

        for (int i1 = 0; i1 < frameTimes.length; i1++)
        {
            int k1 = ((i1 - numRecordedFrameTimes & frameTimes.length - 1) * 255) / frameTimes.length;
            int j2 = (k1 * k1) / 255;
            j2 = (j2 * j2) / 255;
            int i3 = (j2 * j2) / 255;
            i3 = (i3 * i3) / 255;

            if (frameTimes[i1] > l)
            {
                tessellator.setColorOpaque_I(0xff000000 + j2 * 0x10000);
            }
            else
            {
                tessellator.setColorOpaque_I(0xff000000 + j2 * 256);
            }

            long l3 = frameTimes[i1] / 0x30d40L;
            long l4 = tickTimes[i1] / 0x30d40L;
            tessellator.addVertex((float)i1 + 0.5F, (float)((long)displayHeight - l3) + 0.5F, 0.0D);
            tessellator.addVertex((float)i1 + 0.5F, (float)displayHeight + 0.5F, 0.0D);
            tessellator.setColorOpaque_I(0xff000000 + j2 * 0x10000 + j2 * 256 + j2 * 1);
            tessellator.addVertex((float)i1 + 0.5F, (float)((long)displayHeight - l3) + 0.5F, 0.0D);
            tessellator.addVertex((float)i1 + 0.5F, (float)((long)displayHeight - (l3 - l4)) + 0.5F, 0.0D);
        }

        tessellator.draw();
        int j1 = 160;
        int i2 = displayWidth - j1 - 10;
        int k2 = displayHeight - j1 * 2;
        GL11.glEnable(GL11.GL_BLEND);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(0, 200);
        tessellator.addVertex((float)i2 - (float)j1 * 1.1F, (float)k2 - (float)j1 * 0.6F - 16F, 0.0D);
        tessellator.addVertex((float)i2 - (float)j1 * 1.1F, k2 + j1 * 2, 0.0D);
        tessellator.addVertex((float)i2 + (float)j1 * 1.1F, k2 + j1 * 2, 0.0D);
        tessellator.addVertex((float)i2 + (float)j1 * 1.1F, (float)k2 - (float)j1 * 0.6F - 16F, 0.0D);
        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        double d = 0.0D;

        for (int j3 = 0; j3 < list.size(); j3++)
        {
            ProfilerResult profilerresult1 = (ProfilerResult)list.get(j3);
            int i4 = MathHelper.floor_double(profilerresult1.sectionPercentage / 4D) + 1;
            tessellator.startDrawing(6);
            tessellator.setColorOpaque_I(profilerresult1.getDisplayColor());
            tessellator.addVertex(i2, k2, 0.0D);

            for (int k4 = i4; k4 >= 0; k4--)
            {
                float f = (float)(((d + (profilerresult1.sectionPercentage * (double)k4) / (double)i4) * Math.PI * 2D) / 100D);
                float f2 = MathHelper.sin(f) * (float)j1;
                float f4 = MathHelper.cos(f) * (float)j1 * 0.5F;
                tessellator.addVertex((float)i2 + f2, (float)k2 - f4, 0.0D);
            }

            tessellator.draw();
            tessellator.startDrawing(5);
            tessellator.setColorOpaque_I((profilerresult1.getDisplayColor() & 0xfefefe) >> 1);

            for (int i5 = i4; i5 >= 0; i5--)
            {
                float f1 = (float)(((d + (profilerresult1.sectionPercentage * (double)i5) / (double)i4) * Math.PI * 2D) / 100D);
                float f3 = MathHelper.sin(f1) * (float)j1;
                float f5 = MathHelper.cos(f1) * (float)j1 * 0.5F;
                tessellator.addVertex((float)i2 + f3, (float)k2 - f5, 0.0D);
                tessellator.addVertex((float)i2 + f3, ((float)k2 - f5) + 10F, 0.0D);
            }

            tessellator.draw();
            d += profilerresult1.sectionPercentage;
        }

        DecimalFormat decimalformat = new DecimalFormat("##0.00");
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        String s = "";

        if (!profilerresult.name.equals("unspecified"))
        {
            s = (new StringBuilder()).append(s).append("[0] ").toString();
        }

        if (profilerresult.name.length() == 0)
        {
            s = (new StringBuilder()).append(s).append("ROOT ").toString();
        }
        else
        {
            s = (new StringBuilder()).append(s).append(profilerresult.name).append(" ").toString();
        }

        int j4 = 0xffffff;
        fontRenderer.drawStringWithShadow(s, i2 - j1, k2 - j1 / 2 - 16, j4);
        fontRenderer.drawStringWithShadow(s = (new StringBuilder()).append(decimalformat.format(profilerresult.globalPercentage)).append("%").toString(), (i2 + j1) - fontRenderer.getStringWidth(s), k2 - j1 / 2 - 16, j4);

        for (int k3 = 0; k3 < list.size(); k3++)
        {
            ProfilerResult profilerresult2 = (ProfilerResult)list.get(k3);
            String s1 = "";

            if (profilerresult2.name.equals("unspecified"))
            {
                s1 = (new StringBuilder()).append(s1).append("[?] ").toString();
            }
            else
            {
                s1 = (new StringBuilder()).append(s1).append("[").append(k3 + 1).append("] ").toString();
            }

            s1 = (new StringBuilder()).append(s1).append(profilerresult2.name).toString();
            fontRenderer.drawStringWithShadow(s1, i2 - j1, k2 + j1 / 2 + k3 * 8 + 20, profilerresult2.getDisplayColor());
            fontRenderer.drawStringWithShadow(s1 = (new StringBuilder()).append(decimalformat.format(profilerresult2.sectionPercentage)).append("%").toString(), (i2 + j1) - 50 - fontRenderer.getStringWidth(s1), k2 + j1 / 2 + k3 * 8 + 20, profilerresult2.getDisplayColor());
            fontRenderer.drawStringWithShadow(s1 = (new StringBuilder()).append(decimalformat.format(profilerresult2.globalPercentage)).append("%").toString(), (i2 + j1) - fontRenderer.getStringWidth(s1), k2 + j1 / 2 + k3 * 8 + 20, profilerresult2.getDisplayColor());
        }
    }

    /**
     * Called when the window is closing. Sets 'running' to false which allows the game loop to exit cleanly.
     */
    public void shutdown()
    {
        running = false;
    }

    /**
     * Will set the focus to ingame if the Minecraft window is the active with focus. Also clears any GUI screen
     * currently displayed
     */
    public void setIngameFocus()
    {
        if (!Display.isActive())
        {
            return;
        }

        if (inGameHasFocus)
        {
            return;
        }
        else
        {
            inGameHasFocus = true;
            mouseHelper.grabMouseCursor();
            displayGuiScreen(null);
            leftClickCounter = 10000;
            return;
        }
    }

    /**
     * Resets the player keystate, disables the ingame focus, and ungrabs the mouse cursor.
     */
    public void setIngameNotInFocus()
    {
        if (!inGameHasFocus)
        {
            return;
        }
        else
        {
            KeyBinding.unPressAllKeys();
            inGameHasFocus = false;
            mouseHelper.ungrabMouseCursor();
            return;
        }
    }

    /**
     * Displays the ingame menu
     */
    public void displayInGameMenu()
    {
        if (currentScreen != null)
        {
            return;
        }
        else
        {
            displayGuiScreen(new GuiIngameMenu());
            return;
        }
    }

    private void sendClickBlockToController(int par1, boolean par2)
    {
        if (!par2)
        {
            leftClickCounter = 0;
        }

        if (par1 == 0 && leftClickCounter > 0)
        {
            return;
        }

        if (par2 && objectMouseOver != null && objectMouseOver.typeOfHit == EnumMovingObjectType.TILE && par1 == 0)
        {
            int i = objectMouseOver.blockX;
            int j = objectMouseOver.blockY;
            int k = objectMouseOver.blockZ;
            field_56453_c.onPlayerDamageBlock(i, j, k, objectMouseOver.sideHit);

            if (field_56455_h.canPlayerEdit(i, j, k))
            {
                effectRenderer.addBlockHitEffects(i, j, k, objectMouseOver.sideHit);
                field_56455_h.swingItem();
            }
        }
        else
        {
            field_56453_c.resetBlockRemoving();
        }
    }

    /**
     * Called whenever the mouse is clicked. Button clicked is 0 for left clicking and 1 for right clicking. Args:
     * buttonClicked
     */
    private void clickMouse(int par1)
    {
        if (par1 == 0 && leftClickCounter > 0)
        {
            return;
        }

        if (par1 == 0)
        {
            field_56455_h.swingItem();
        }

        if (par1 == 1)
        {
            rightClickDelayTimer = 4;
        }

        boolean flag = true;
        ItemStack itemstack = field_56455_h.inventory.getCurrentItem();

        if (objectMouseOver == null)
        {
            if (par1 == 0 && field_56453_c.isNotCreative())
            {
                leftClickCounter = 10;
            }
        }
        else if (objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY)
        {
            if (par1 == 0)
            {
                field_56453_c.attackEntity(field_56455_h, objectMouseOver.entityHit);
            }

            if (par1 == 1 && field_56453_c.func_57518_b(field_56455_h, objectMouseOver.entityHit))
            {
                flag = false;
            }
        }
        else if (objectMouseOver.typeOfHit == EnumMovingObjectType.TILE)
        {
            int i = objectMouseOver.blockX;
            int j = objectMouseOver.blockY;
            int k = objectMouseOver.blockZ;
            int l = objectMouseOver.sideHit;

            if (par1 == 0)
            {
                field_56453_c.clickBlock(i, j, k, objectMouseOver.sideHit);
            }
            else
            {
                int i1 = itemstack == null ? 0 : itemstack.stackSize;

                if (field_56453_c.func_57522_a(field_56455_h, field_56452_f, itemstack, i, j, k, l, objectMouseOver.hitVec))
                {
                    flag = false;
                    field_56455_h.swingItem();
                }

                if (itemstack == null)
                {
                    return;
                }

                if (itemstack.stackSize == 0)
                {
                    field_56455_h.inventory.mainInventory[field_56455_h.inventory.currentItem] = null;
                }
                else if (itemstack.stackSize != i1 || field_56453_c.isInCreativeMode())
                {
                    entityRenderer.itemRenderer.func_9449_b();
                }
            }
        }

        if (flag && par1 == 1)
        {
            ItemStack itemstack1 = field_56455_h.inventory.getCurrentItem();

            if (itemstack1 != null && field_56453_c.sendUseItem(field_56455_h, field_56452_f, itemstack1))
            {
                entityRenderer.itemRenderer.func_9450_c();
            }
        }
    }

    /**
     * Toggles fullscreen mode.
     */
    public void toggleFullscreen()
    {
        try
        {
            fullscreen = !fullscreen;

            if (fullscreen)
            {
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                displayWidth = Display.getDisplayMode().getWidth();
                displayHeight = Display.getDisplayMode().getHeight();

                if (displayWidth <= 0)
                {
                    displayWidth = 1;
                }

                if (displayHeight <= 0)
                {
                    displayHeight = 1;
                }
            }
            else
            {
                if (mcCanvas != null)
                {
                    displayWidth = mcCanvas.getWidth();
                    displayHeight = mcCanvas.getHeight();
                }
                else
                {
                    displayWidth = tempDisplayWidth;
                    displayHeight = tempDisplayHeight;
                }

                if (displayWidth <= 0)
                {
                    displayWidth = 1;
                }

                if (displayHeight <= 0)
                {
                    displayHeight = 1;
                }
            }

            if (currentScreen != null)
            {
                resize(displayWidth, displayHeight);
            }

            Display.setFullscreen(fullscreen);
            Display.update();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    /**
     * Called to resize the current screen.
     */
    private void resize(int par1, int par2)
    {
        displayWidth = par1 > 0 ? par1 : 1;
        displayHeight = par2 > 0 ? par2 : 1;

        if (currentScreen != null)
        {
            ScaledResolution scaledresolution = new ScaledResolution(gameSettings, par1, par2);
            int i = scaledresolution.getScaledWidth();
            int j = scaledresolution.getScaledHeight();
            currentScreen.setWorldAndResolution(this, i, j);
        }
    }

    /**
     * Runs the current tick.
     */
    public void runTick()
    {
        if (rightClickDelayTimer > 0)
        {
            rightClickDelayTimer--;
        }

        Profiler.startSection("stats");
        statFileWriter.func_27178_d();
        Profiler.endStartSection("gui");

        if (!isGamePaused)
        {
            ingameGUI.updateTick();
        }

        Profiler.endStartSection("pick");
        entityRenderer.getMouseOver(1.0F);
        Profiler.endStartSection("gameMode");

        if (!isGamePaused && field_56452_f != null)
        {
            field_56453_c.updateController();
        }

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderEngine.getTexture("/terrain.png"));
        Profiler.endStartSection("textures");

        if (!isGamePaused)
        {
            renderEngine.updateDynamicTextures();
        }

        if (currentScreen == null && field_56455_h != null)
        {
            if (field_56455_h.getHealth() <= 0)
            {
                displayGuiScreen(null);
            }
            else if (field_56455_h.isPlayerSleeping() && field_56452_f != null)
            {
                displayGuiScreen(new GuiSleepMP());
            }
        }
        else if (currentScreen != null && (currentScreen instanceof GuiSleepMP) && !field_56455_h.isPlayerSleeping())
        {
            displayGuiScreen(null);
        }

        if (currentScreen != null)
        {
            leftClickCounter = 10000;
        }

        if (currentScreen != null)
        {
            currentScreen.handleInput();

            if (currentScreen != null)
            {
                currentScreen.guiParticles.update();
                currentScreen.updateScreen();
            }
        }

        if (currentScreen == null || currentScreen.allowUserInput)
        {
            Profiler.endStartSection("mouse");

            do
            {
                if (!Mouse.next())
                {
                    break;
                }

                KeyBinding.setKeyBindState(Mouse.getEventButton() - 100, Mouse.getEventButtonState());

                if (Mouse.getEventButtonState())
                {
                    KeyBinding.onTick(Mouse.getEventButton() - 100);
                }

                long l = System.currentTimeMillis() - systemTime;

                if (l <= 200L)
                {
                    int k = Mouse.getEventDWheel();

                    if (k != 0)
                    {
                        field_56455_h.inventory.changeCurrentItem(k);

                        if (gameSettings.noclip)
                        {
                            if (k > 0)
                            {
                                k = 1;
                            }

                            if (k < 0)
                            {
                                k = -1;
                            }

                            gameSettings.noclipRate += (float)k * 0.25F;
                        }
                    }

                    if (currentScreen == null)
                    {
                        if (!inGameHasFocus && Mouse.getEventButtonState())
                        {
                            setIngameFocus();
                        }
                    }
                    else if (currentScreen != null)
                    {
                        currentScreen.handleMouseInput();
                    }
                }
            }
            while (true);

            if (leftClickCounter > 0)
            {
                leftClickCounter--;
            }

            Profiler.endStartSection("keyboard");

            do
            {
                if (!Keyboard.next())
                {
                    break;
                }

                KeyBinding.setKeyBindState(Keyboard.getEventKey(), Keyboard.getEventKeyState());

                if (Keyboard.getEventKeyState())
                {
                    KeyBinding.onTick(Keyboard.getEventKey());
                }

                if (Keyboard.getEventKeyState())
                {
                    if (Keyboard.getEventKey() == 87)
                    {
                        toggleFullscreen();
                    }
                    else
                    {
                        if (currentScreen != null)
                        {
                            currentScreen.handleKeyboardInput();
                        }
                        else
                        {
                            if (Keyboard.getEventKey() == 1)
                            {
                                displayInGameMenu();
                            }

                            if (Keyboard.getEventKey() == 31 && Keyboard.isKeyDown(61))
                            {
                                forceReload();
                            }

                            if (Keyboard.getEventKey() == 20 && Keyboard.isKeyDown(61))
                            {
                                renderEngine.refreshTextures();
                            }

                            if (Keyboard.getEventKey() == 33 && Keyboard.isKeyDown(61))
                            {
                                boolean flag = Keyboard.isKeyDown(42) | Keyboard.isKeyDown(54);
                                gameSettings.setOptionValue(EnumOptions.RENDER_DISTANCE, flag ? -1 : 1);
                            }

                            if (Keyboard.getEventKey() == 30 && Keyboard.isKeyDown(61))
                            {
                                renderGlobal.loadRenderers();
                            }

                            if (Keyboard.getEventKey() == 59)
                            {
                                gameSettings.hideGUI = !gameSettings.hideGUI;
                            }

                            if (Keyboard.getEventKey() == 61)
                            {
                                gameSettings.showDebugInfo = !gameSettings.showDebugInfo;
                                gameSettings.field_50119_G = !GuiScreen.func_50049_m();
                            }

                            if (Keyboard.getEventKey() == 63)
                            {
                                gameSettings.thirdPersonView++;

                                if (gameSettings.thirdPersonView > 2)
                                {
                                    gameSettings.thirdPersonView = 0;
                                }
                            }

                            if (Keyboard.getEventKey() == 66)
                            {
                                gameSettings.smoothCamera = !gameSettings.smoothCamera;
                            }
                        }

                        for (int i = 0; i < 9; i++)
                        {
                            if (Keyboard.getEventKey() == 2 + i)
                            {
                                field_56455_h.inventory.currentItem = i;
                            }
                        }

                        if (gameSettings.showDebugInfo && gameSettings.field_50119_G)
                        {
                            if (Keyboard.getEventKey() == 11)
                            {
                                updateDebugProfilerName(0);
                            }

                            int j = 0;

                            while (j < 9)
                            {
                                if (Keyboard.getEventKey() == 2 + j)
                                {
                                    updateDebugProfilerName(j + 1);
                                }

                                j++;
                            }
                        }
                    }
                }
            }
            while (true);

            boolean flag1 = gameSettings.field_55382_n != 2;

            for (; gameSettings.keyBindInventory.isPressed(); displayGuiScreen(new GuiInventory(field_56455_h))) { }

            for (; gameSettings.keyBindDrop.isPressed(); field_56455_h.dropOneItem()) { }

            for (; gameSettings.keyBindChat.isPressed() && flag1; displayGuiScreen(new GuiChat())) { }

            if (currentScreen == null && gameSettings.field_55381_F.isPressed() && flag1)
            {
                displayGuiScreen(new GuiChat("/"));
            }

            if (field_56455_h.isUsingItem())
            {
                if (!gameSettings.keyBindUseItem.pressed)
                {
                    field_56453_c.onStoppedUsingItem(field_56455_h);
                }

                while (gameSettings.keyBindAttack.isPressed()) ;

                while (gameSettings.keyBindUseItem.isPressed()) ;

                while (gameSettings.keyBindPickBlock.isPressed()) ;
            }
            else
            {
                for (; gameSettings.keyBindAttack.isPressed(); clickMouse(0)) { }

                for (; gameSettings.keyBindUseItem.isPressed(); clickMouse(1)) { }

                for (; gameSettings.keyBindPickBlock.isPressed(); clickMiddleMouseButton()) { }
            }

            if (gameSettings.keyBindUseItem.pressed && rightClickDelayTimer == 0 && !field_56455_h.isUsingItem())
            {
                clickMouse(1);
            }

            sendClickBlockToController(0, currentScreen == null && gameSettings.keyBindAttack.pressed && inGameHasFocus);
        }

        if (field_56452_f != null)
        {
            if (field_56455_h != null)
            {
                joinPlayerCounter++;

                if (joinPlayerCounter == 30)
                {
                    joinPlayerCounter = 0;
                    field_56452_f.joinEntityInSurroundings(field_56455_h);
                }
            }

            Profiler.endStartSection("gameRenderer");

            if (!isGamePaused)
            {
                entityRenderer.updateRenderer();
            }

            Profiler.endStartSection("levelRenderer");

            if (!isGamePaused)
            {
                renderGlobal.updateClouds();
            }

            Profiler.endStartSection("level");

            if (!isGamePaused)
            {
                if (field_56452_f.lightningFlash > 0)
                {
                    field_56452_f.lightningFlash--;
                }

                field_56452_f.updateEntities();
            }

            if (!isGamePaused)
            {
                field_56452_f.setAllowedSpawnTypes(field_56452_f.difficultySetting > 0, true);
                field_56452_f.tick();
            }

            Profiler.endStartSection("animateTick");

            if (!isGamePaused && field_56452_f != null)
            {
                field_56452_f.func_56843_d(MathHelper.floor_double(field_56455_h.posX), MathHelper.floor_double(field_56455_h.posY), MathHelper.floor_double(field_56455_h.posZ));
            }

            Profiler.endStartSection("particles");

            if (!isGamePaused)
            {
                effectRenderer.updateEffects();
            }
        }
        else if (field_56457_an != null)
        {
            Profiler.endStartSection("pendingConnection");
            field_56457_an.processReadPackets();
        }

        Profiler.endSection();
        systemTime = System.currentTimeMillis();
    }

    /**
     * Forces a reload of the sound manager and all the resources. Called in game by holding 'F3' and pressing 'S'.
     */
    private void forceReload()
    {
        System.out.println("FORCING RELOAD!");
        sndManager = new SoundManager();
        sndManager.loadSoundSettings(gameSettings);
        downloadResourcesThread.reloadResources();
    }

    public void func_58039_a(String par1Str, String par2Str, WorldSettings par3WorldSettings)
    {
        func_56445_a(null);
        System.gc();
        ISaveHandler isavehandler = saveLoader.getSaveLoader(par1Str, false);
        WorldInfo worldinfo = isavehandler.loadWorldInfo();

        if (worldinfo == null && par3WorldSettings != null)
        {
            statFileWriter.readStat(StatList.createWorldStat, 1);
            worldinfo = new WorldInfo(par3WorldSettings, par1Str);
            isavehandler.saveWorldInfo(worldinfo);
        }

        if (par3WorldSettings == null)
        {
            par3WorldSettings = new WorldSettings(worldinfo);
        }

        statFileWriter.readStat(StatList.startGameStat, 1);
        field_56458_ac = new IntegratedServer(this, par1Str, par2Str, par3WorldSettings);
        field_56458_ac.func_56291_q();
        field_56456_ao = true;
        loadingScreen.displaySavingString(StatCollector.translateToLocal("menu.loadingLevel"));

        while (!field_56458_ac.func_56365_ab())
        {
            String s = field_56458_ac.func_58030_b();

            if (s != null)
            {
                loadingScreen.displayLoadingString(StatCollector.translateToLocal(s));
            }
            else
            {
                loadingScreen.displayLoadingString("");
            }

            try
            {
                Thread.sleep(200L);
            }
            catch (InterruptedException interruptedexception) { }
        }

        displayGuiScreen(null);

        try
        {
            NetClientHandler netclienthandler = new NetClientHandler(this, field_56458_ac);
            field_56457_an = netclienthandler.func_56761_e();
        }
        catch (IOException ioexception)
        {
            func_55071_b(func_55067_c(new CrashReport("Connecting to integrated server", ioexception)));
        }
    }

    public void func_56445_a(WorldClient par1WorldClient)
    {
        func_56441_a(par1WorldClient, "");
    }

    public void func_56441_a(WorldClient par1WorldClient, String par2Str)
    {
        statFileWriter.syncStats();

        if (par1WorldClient == null)
        {
            NetClientHandler netclienthandler = getSendQueue();

            if (netclienthandler != null)
            {
                netclienthandler.func_56760_b();
            }

            if (field_56457_an != null)
            {
                field_56457_an.func_57274_f();
            }

            if (field_56458_ac != null)
            {
                field_56458_ac.func_56286_k();
            }

            field_56458_ac = null;
        }

        renderViewEntity = null;
        field_56457_an = null;

        if (loadingScreen != null)
        {
            loadingScreen.printText(par2Str);
            loadingScreen.displayLoadingString("");
        }

        if (par1WorldClient == null && field_56452_f != null)
        {
            if (texturePackList.func_57498_a())
            {
                texturePackList.func_57505_b();
            }

            func_56449_a(null);
            field_56456_ao = false;
        }

        sndManager.playStreaming(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        field_56452_f = par1WorldClient;

        if (par1WorldClient != null)
        {
            if (renderGlobal != null)
            {
                renderGlobal.func_56647_a(par1WorldClient);
            }

            if (effectRenderer != null)
            {
                effectRenderer.clearEffects(par1WorldClient);
            }

            if (field_56455_h == null)
            {
                field_56455_h = field_56453_c.func_57523_a(par1WorldClient);
                field_56453_c.flipPlayer(field_56455_h);
            }

            field_56455_h.preparePlayerToSpawn();
            par1WorldClient.spawnEntityInWorld(field_56455_h);
            field_56455_h.movementInput = new MovementInputFromOptions(gameSettings);
            field_56453_c.func_57520_a(field_56455_h);
            renderViewEntity = field_56455_h;
        }
        else
        {
            saveLoader.flushCache();
            field_56455_h = null;
        }

        System.gc();
        systemTime = 0L;
    }

    /**
     * Installs a resource. Currently only sounds are download so this method just adds them to the SoundManager.
     */
    public void installResource(String par1Str, File par2File)
    {
        int i = par1Str.indexOf("/");
        String s = par1Str.substring(0, i);
        par1Str = par1Str.substring(i + 1);

        if (s.equalsIgnoreCase("sound") || s.equalsIgnoreCase("newsound"))
        {
            sndManager.addSound(par1Str, par2File);
        }
        else if (s.equalsIgnoreCase("streaming"))
        {
            sndManager.addStreaming(par1Str, par2File);
        }
        else if (s.equalsIgnoreCase("music") || s.equalsIgnoreCase("newmusic"))
        {
            sndManager.addMusic(par1Str, par2File);
        }
    }

    /**
     * A String of renderGlobal.getDebugInfoRenders
     */
    public String debugInfoRenders()
    {
        return renderGlobal.getDebugInfoRenders();
    }

    /**
     * Gets the information in the F3 menu about how many entities are infront/around you
     */
    public String getEntityDebug()
    {
        return renderGlobal.getDebugInfoEntities();
    }

    /**
     * Gets the name of the world's current chunk provider
     */
    public String getWorldProviderName()
    {
        return field_56452_f.getProviderName();
    }

    /**
     * A String of how many entities are in the world
     */
    public String debugInfoEntities()
    {
        return (new StringBuilder()).append("P: ").append(effectRenderer.getStatistics()).append(". T: ").append(field_56452_f.getDebugLoadedEntities()).toString();
    }

    public void func_56438_a(int par1)
    {
        field_56452_f.setSpawnLocation();
        field_56452_f.func_56844_h();
        int i = 0;

        if (field_56455_h != null)
        {
            i = field_56455_h.entityId;
            field_56452_f.setEntityDead(field_56455_h);
        }

        renderViewEntity = null;
        field_56455_h = field_56453_c.func_57523_a(field_56452_f);
        field_56455_h.dimension = par1;
        renderViewEntity = field_56455_h;
        field_56455_h.preparePlayerToSpawn();
        field_56452_f.spawnEntityInWorld(field_56455_h);
        field_56453_c.flipPlayer(field_56455_h);
        field_56455_h.movementInput = new MovementInputFromOptions(gameSettings);
        field_56455_h.entityId = i;
        field_56453_c.func_57520_a(field_56455_h);

        if (currentScreen instanceof GuiGameOver)
        {
            displayGuiScreen(null);
        }
    }

    void func_55069_a(boolean par1)
    {
        field_55073_an = par1;
    }

    public final boolean func_55063_q()
    {
        return field_55073_an;
    }

    /**
     * get the client packet send queue
     */
    public NetClientHandler getSendQueue()
    {
        if (field_56455_h != null)
        {
            return field_56455_h.sendQueue;
        }
        else
        {
            return null;
        }
    }

    public static void main(String par0ArrayOfStr[])
    {
        HashMap hashmap = new HashMap();
        boolean flag = false;
        boolean flag1 = true;
        boolean flag2 = false;
        Object obj = null;
        char c = 25565;
        String s = null;
        String s1 = null;
        String s2 = (new StringBuilder()).append("Player").append(System.currentTimeMillis() % 1000L).toString();

        if (par0ArrayOfStr.length > 0)
        {
            s2 = par0ArrayOfStr[0];
        }

        String s3 = "-";

        if (par0ArrayOfStr.length > 1)
        {
            s3 = par0ArrayOfStr[1];
        }

        for (int i = 2; i < par0ArrayOfStr.length; i++)
        {
            String s4 = par0ArrayOfStr[i];
            String s5 = i != par0ArrayOfStr.length - 1 ? par0ArrayOfStr[i + 1] : null;
            boolean flag3 = false;

            if (s4.equals("-demo") || s4.equals("--demo"))
            {
                flag = true;
            }
            else if (s4.equals("--server-path") && s5 != null)
            {
                flag3 = true;
                s = s5;
            }
            else if (s4.equals("--server-entry") && s5 != null)
            {
                flag3 = true;
                s1 = s5;
            }
            else if (s4.equals("--applet"))
            {
                flag1 = false;
            }

            if (flag3)
            {
                i++;
            }
        }

        hashmap.put("demo", (new StringBuilder()).append("").append(flag).toString());
        hashmap.put("stand-alone", (new StringBuilder()).append("").append(flag1).toString());
        hashmap.put("username", s2);
        hashmap.put("fullscreen", (new StringBuilder()).append("").append(flag2).toString());
        hashmap.put("sessionid", s3);
        hashmap.put("server", obj);
        hashmap.put("port", (new StringBuilder()).append("").append(c).toString());
        hashmap.put("server-loc", s);
        hashmap.put("server-entrty", s1);
        Frame frame = new Frame();
        frame.setTitle("Minecraft");
        frame.setBackground(Color.BLACK);
        JPanel jpanel = new JPanel();
        frame.setLayout(new BorderLayout());
        jpanel.setPreferredSize(new Dimension(854, 480));
        frame.add(jpanel, "Center");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new GameWindowListener());
        MinecraftFakeLauncher minecraftfakelauncher = new MinecraftFakeLauncher(hashmap);
        MinecraftApplet minecraftapplet = new MinecraftApplet();
        minecraftapplet.setStub(minecraftfakelauncher);
        minecraftfakelauncher.setLayout(new BorderLayout());
        minecraftfakelauncher.add(minecraftapplet, "Center");
        minecraftfakelauncher.validate();
        frame.removeAll();
        frame.setLayout(new BorderLayout());
        frame.add(minecraftfakelauncher, "Center");
        frame.validate();
        minecraftapplet.init();
        minecraftapplet.start();
    }

    public static boolean isGuiEnabled()
    {
        return theMinecraft == null || !theMinecraft.gameSettings.hideGUI;
    }

    public static boolean isFancyGraphicsEnabled()
    {
        return theMinecraft != null && theMinecraft.gameSettings.fancyGraphics;
    }

    /**
     * Returns if ambient occlusion is enabled
     */
    public static boolean isAmbientOcclusionEnabled()
    {
        return theMinecraft != null && theMinecraft.gameSettings.ambientOcclusion;
    }

    public static boolean isDebugInfoEnabled()
    {
        return theMinecraft != null && theMinecraft.gameSettings.showDebugInfo;
    }

    /**
     * Returns true if string begins with '/'
     */
    public boolean lineIsCommand(String par1Str)
    {
        return par1Str.startsWith("/") ? false : false;
    }

    /**
     * Called when the middle mouse button gets clicked
     */
    private void clickMiddleMouseButton()
    {
        if (objectMouseOver == null)
        {
            return;
        }

        boolean flag = field_56455_h.capabilities.isCreativeMode;
        int j = 0;
        boolean flag1 = false;
        int i;

        if (objectMouseOver.typeOfHit == EnumMovingObjectType.TILE)
        {
            int k = objectMouseOver.blockX;
            int i1 = objectMouseOver.blockY;
            int j1 = objectMouseOver.blockZ;
            Block block = Block.blocksList[field_56452_f.getBlockId(k, i1, j1)];

            if (block == null)
            {
                return;
            }

            i = block.func_55201_e(field_56452_f, k, i1, j1);

            if (i == 0)
            {
                return;
            }

            flag1 = Item.itemsList[i].getHasSubtypes();
            int k1 = i < 256 ? i : block.blockID;
            j = Block.blocksList[k1].func_55199_h(field_56452_f, k, i1, j1);
        }
        else if (objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY && objectMouseOver.entityHit != null && flag)
        {
            if (objectMouseOver.entityHit instanceof EntityPainting)
            {
                i = Item.painting.shiftedIndex;
            }
            else if (objectMouseOver.entityHit instanceof EntityMinecart)
            {
                EntityMinecart entityminecart = (EntityMinecart)objectMouseOver.entityHit;

                if (entityminecart.minecartType == 2)
                {
                    i = Item.minecartPowered.shiftedIndex;
                }
                else if (entityminecart.minecartType == 1)
                {
                    i = Item.minecartCrate.shiftedIndex;
                }
                else
                {
                    i = Item.minecartEmpty.shiftedIndex;
                }
            }
            else if (objectMouseOver.entityHit instanceof EntityBoat)
            {
                i = Item.boat.shiftedIndex;
            }
            else
            {
                i = Item.monsterPlacer.shiftedIndex;
                j = EntityList.getEntityID(objectMouseOver.entityHit);
                flag1 = true;

                if (j <= 0 || !EntityList.entityEggs.containsKey(Integer.valueOf(j)))
                {
                    return;
                }
            }
        }
        else
        {
            return;
        }

        field_56455_h.inventory.setCurrentItem(i, j, flag1, flag);

        if (flag)
        {
            int l = (field_56455_h.inventorySlots.inventorySlots.size() - 9) + field_56455_h.inventory.currentItem;
            field_56453_c.sendSlotPacket(field_56455_h.inventory.getStackInSlot(field_56455_h.inventory.currentItem), l);
        }
    }

    public CrashReport func_55067_c(CrashReport par1CrashReport)
    {
        par1CrashReport.func_55366_a("LWJGL", new CallableLWJGLVersion(this));
        par1CrashReport.func_55366_a("OpenGL", new CallableGLInfo(this));
        par1CrashReport.func_55366_a("Is Modded", new CallableModded(this));

        if (field_56452_f != null)
        {
            field_56452_f.func_55266_a(par1CrashReport);
        }

        return par1CrashReport;
    }

    public ServerLauncher func_56443_w()
    {
        return field_56459_ab;
    }

    public static Minecraft func_55068_z()
    {
        return theMinecraft;
    }

    public void func_56447_y()
    {
        field_56460_ag = true;
    }

    public void func_56176_a(PlayerUsageSnooper par1PlayerUsageSnooper)
    {
        par1PlayerUsageSnooper.func_52022_a("fps", Integer.valueOf(field_56461_ae));
        par1PlayerUsageSnooper.func_52022_a("texpack_name", texturePackList.func_55278_c().func_55180_b());
        par1PlayerUsageSnooper.func_52022_a("texpack_resolution", Integer.valueOf(texturePackList.func_55278_c().func_56598_e()));
    }

    public void func_56177_b(PlayerUsageSnooper par1PlayerUsageSnooper)
    {
        par1PlayerUsageSnooper.func_52022_a("opengl_version", GL11.glGetString(GL11.GL_VERSION));
        par1PlayerUsageSnooper.func_52022_a("opengl_vendor", GL11.glGetString(GL11.GL_VENDOR));
        par1PlayerUsageSnooper.func_52022_a("client_brand", ClientBrandRetriever.func_56462_getClientModName());
    }

    public void func_56449_a(ServerData par1ServerData)
    {
        field_56454_a = par1ServerData;
    }

    public ServerData func_56446_z()
    {
        return field_56454_a;
    }

    public boolean func_56444_A()
    {
        return field_56456_ao;
    }

    public boolean func_56439_B()
    {
        return field_56456_ao && field_56458_ac != null;
    }

    public IntegratedServer func_58040_C()
    {
        return field_56458_ac;
    }
}
