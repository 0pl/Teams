package com.daposeidonguy.teamsmod.client.gui.toasts;

import com.daposeidonguy.teamsmod.client.keybind.KeyBindHandler;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import java.awt.*;

public class ToastInvite implements IToast {

    private final String teamName;
    public boolean accepted = false;
    private boolean firstDraw = true;
    private long firstDrawTime;

    public ToastInvite(final String teamName) {
        this.teamName = teamName;
    }

    @Override
    @Nonnull
    public Visibility draw(@Nonnull final GuiToast toastGui, final long delta) {
        if (firstDraw) {
            firstDrawTime = delta;
            firstDraw = false;
        }
        if (accepted) {
            return IToast.Visibility.HIDE;
        }
        toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        toastGui.drawTexturedModalRect(0, 0, 0, 64, 160, 32);
        toastGui.getMinecraft().fontRenderer.drawString(I18n.format("teamsmod.toast.invite") + this.teamName, 22, 7, Color.WHITE.getRGB());
        String keyName = "\"" + Keyboard.getKeyName(KeyBindHandler.acceptInvite.getKeyCode()) + "\"";
        toastGui.getMinecraft().fontRenderer.drawString(I18n.format("teamsmod.toast.accept", keyName), 22, 18, -16777216);

        return delta - this.firstDrawTime < 15000L && this.teamName != null ? IToast.Visibility.SHOW : IToast.Visibility.HIDE;
    }
}
