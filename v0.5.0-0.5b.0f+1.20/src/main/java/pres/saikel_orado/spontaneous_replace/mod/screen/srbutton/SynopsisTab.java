package pres.saikel_orado.spontaneous_replace.mod.screen.srbutton;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.text.Text;

import java.util.Objects;


/**
 * <b style="color:FFC800"><font size="+2">SynopsisTab：自然更替简介选项</font></b>
 * <p><i><b style="color:FFC800"><font size="+1">自然更替模组按钮的简介选项</font></b></i></p>
 * <style="color:FFC800">
 *
 * @author 刘 Saikel Orado 又称 “游戏极客-Saikel”
 * <p>Saikel Orado Liu aka ”GameGeek-Saikel“</p>
 * @version 4.0
 * | 创建于 2023/3/14 22:37
 */
@Environment(EnvType.CLIENT)
final class SynopsisTab extends GridScreenTab {
    public static final Text SR_SYNOPSIS_TEXT = Text.translatable("text.spontaneous_replace.mod_synopsis");
    public static final Text SR_INTRODUCTION_TITLE = Text.translatable("title.spontaneous_replace.mod_introduction");
    public static final Text SR_INTRODUCTION_TEXT = Text.translatable("text.spontaneous_replace.mod_introduction");
    public static final Text SR_VISION_TITLE = Text.translatable("title.spontaneous_replace.mod_vision");
    public static final Text SR_VISION_TEXT = Text.translatable("text.spontaneous_replace.mod_vision");

    /**
     * 渲染提供函数, 所需要使用的所有特性都要在此函数中注册
     */
    public void render(DrawContext context, TextRenderer textRenderer, int width, int height) {
        context.drawTextWithShadow(textRenderer, SR_INTRODUCTION_TITLE,
                width / 30, height / 8, 0xFFFFFF);
        MultilineText.create(textRenderer, SR_INTRODUCTION_TEXT, width - 50)
                .drawWithShadow(context, width / 15, (int) (height / 5.5), 10, 0xFFFFFF);
        context.drawTextWithShadow(textRenderer, SR_VISION_TITLE,
                width / 30, (int) (height / 8 * 3.8), 0xFFFFFF);
        MultilineText.create(textRenderer, SR_VISION_TEXT, width - 50)
                .drawWithShadow(context, width / 15, (int) (height / 1.85), 10, 0xFFFFFF);
    }

    /**
     * 是否是这个按钮选项卡
     */
    public static boolean isSynopsisTab(TabManager tabManager) {
        return Objects.requireNonNull(tabManager.getCurrentTab()).getTitle().equals(SR_SYNOPSIS_TEXT);
    }

    /**
     * 选项卡构建
     */
    public SynopsisTab() {
        super(SR_SYNOPSIS_TEXT);
    }
}