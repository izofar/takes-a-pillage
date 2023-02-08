package com.izofar.takesapillage.client.gui;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.config.ModConfigManager;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.client.settings.BooleanOption;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModConfigScreen extends Screen {

    private static final int TITLE_HEIGHT = 8;
    private static final int OPTIONS_LIST_TOP_HEIGHT = 24;
    private static final int OPTIONS_LIST_BOTTOM_OFFSET = 32;
    private static final int OPTIONS_LIST_ITEM_HEIGHT = 25;

    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;
    private static final int DONE_BUTTON_TOP_OFFSET = 26;

    private OptionsRowList optionsRowList;
    private final Screen parentScreen;

    private static final ModConfigManager CONFIG_INSTANCE = ModConfigManager.getInstance();

    public ModConfigScreen(Screen parentScreen) {
        super(new TranslationTextComponent("takesapillage.configGui.title"));
        this.parentScreen = parentScreen;
    }

    @Override
    protected void init() {

        this.optionsRowList = new OptionsRowList(
                this.minecraft, this.width, this.height,
                OPTIONS_LIST_TOP_HEIGHT,
                this.height - OPTIONS_LIST_BOTTOM_OFFSET,
                OPTIONS_LIST_ITEM_HEIGHT
        );

        this.optionsRowList.addBig(new BooleanOption(
                TakesAPillageMod.MODID + ".configGui.replaceIronGolems.title",
                CONFIG_INSTANCE::getReplaceIronGolems,
                CONFIG_INSTANCE::setReplaceIronGolems
        ));

        this.optionsRowList.addBig(new SliderPercentageOption(
                TakesAPillageMod.MODID + ".configGui.peacefulGolemRate.title",
                0, 1, 0.01F,
                CONFIG_INSTANCE::getPeacefulReplaceIronGolems,
                CONFIG_INSTANCE::setPeacefulReplaceIronGolems,
                ModConfigScreen::genericPercentageLabel
        ));

        this.optionsRowList.addBig(new SliderPercentageOption(
                TakesAPillageMod.MODID + ".configGui.easyGolemRate.title",
                0, 1, 0.01F,
                CONFIG_INSTANCE::getEasyReplaceIronGolems,
                CONFIG_INSTANCE::setEasyReplaceIronGolems,
                ModConfigScreen::genericPercentageLabel
        ));

        this.optionsRowList.addBig(new SliderPercentageOption(
                TakesAPillageMod.MODID + ".configGui.normalGolemRate.title",
                0, 1, 0.01F,
                CONFIG_INSTANCE::getNormalReplaceIronGolems,
                CONFIG_INSTANCE::setNormalReplaceIronGolems,
                ModConfigScreen::genericPercentageLabel
        ));

        this.optionsRowList.addBig(new SliderPercentageOption(
                TakesAPillageMod.MODID + ".configGui.hardGolemRate.title",
                0, 1, 0.01F,
                CONFIG_INSTANCE::getHardReplaceIronGolems,
                CONFIG_INSTANCE::setHardReplaceIronGolems,
                ModConfigScreen::genericPercentageLabel
        ));

        this.optionsRowList.addBig(new BooleanOption(
                TakesAPillageMod.MODID + ".configGui.removeBadOmen.title",
                CONFIG_INSTANCE::getRemoveBadOmen,
                CONFIG_INSTANCE::setRemoveBadOmen
        ));

        this.optionsRowList.addBig(new BooleanOption(
                TakesAPillageMod.MODID + ".configGui.pillageSiegesOccur.title",
                CONFIG_INSTANCE::getPillageSiegesOccur,
                CONFIG_INSTANCE::setPillageSiegesOccur
        ));

        this.addWidget(this.optionsRowList);

        this.addButton(new Button(
                (this.width - BUTTON_WIDTH) / 2,
                this.height - DONE_BUTTON_TOP_OFFSET,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                new TranslationTextComponent("gui.done"),
                button -> this.onClose()
        ));
    }

    @Override
    public void onClose() {
        CONFIG_INSTANCE.save();
        this.minecraft.setScreen(parentScreen);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        this.optionsRowList.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, this.title.getString(), this.width / 2, TITLE_HEIGHT, 0xFFFFFF);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    private static ITextComponent genericPercentageLabel(GameSettings options, SliderPercentageOption percentageOption){
        double d0 = percentageOption.get(options);
        return new TranslationTextComponent("options.percent_value", percentageOption.getCaption(), (int)(d0 * 100.0D));
    }
}
