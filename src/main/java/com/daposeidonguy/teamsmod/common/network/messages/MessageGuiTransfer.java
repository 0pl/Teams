package com.daposeidonguy.teamsmod.common.network.messages;

import com.daposeidonguy.teamsmod.common.config.TeamConfig;
import com.daposeidonguy.teamsmod.common.inventory.InterfaceTransfer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.UUID;
import java.util.function.Supplier;

/* Sent/received when a player opens Item Transfer GUI */
public class MessageGuiTransfer extends AbstractMessage {

    public MessageGuiTransfer(final PacketBuffer buf) {
        super(buf);
    }

    public MessageGuiTransfer(final UUID id, final String name) {
        tag.putString("id", id.toString());
        tag.putString("name", name);
    }

    public void onMessage(final Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (EffectiveSide.get().isServer() && !TeamConfig.disableInventoryTransfer) {
                if (!tag.getString("id").equals("") && !tag.getString("name").equals("")) {
                    ServerPlayerEntity p = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUUID(UUID.fromString(tag.getString("id")));
                    if (p != null) {
                        NetworkHooks.openGui(p, new InterfaceTransfer(tag.getString("name")), buf -> buf.writeString(tag.getString("name")));
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
