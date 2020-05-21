package com.daposeidonguy.teamsmod.common.storage;

import com.daposeidonguy.teamsmod.TeamsMod;
import com.daposeidonguy.teamsmod.common.network.PacketHandler;
import com.daposeidonguy.teamsmod.common.network.messages.MessageSaveData;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.network.PacketDistributor;

/* Handles events relating to updating team save data */
@Mod.EventBusSubscriber(modid = TeamsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StorageEvents {

    public static TeamDataManager data;

    /* Sends SaveData packet to player on login */
    @SubscribeEvent
    public static void playerLogIn(final PlayerEvent.PlayerLoggedInEvent event) {
        if (EffectiveSide.get().isServer()) {
            event.getPlayer().getPersistentData().putBoolean("teamChat", false);
            PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new MessageSaveData(event.getPlayer().getServer().getWorld(DimensionType.OVERWORLD)));
            PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new MessageSaveData(event.getPlayer().getServer().getWorld(DimensionType.OVERWORLD)));
        }
    }

    /* Gets savedata when the server starts */
    @SubscribeEvent
    public static void serverStart(final FMLServerStartingEvent event) {
        data = TeamDataManager.get(event.getServer().getWorld(DimensionType.OVERWORLD));
        if (event.getServer().isSinglePlayer()) {
            PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new MessageSaveData(event.getServer().getWorld(DimensionType.OVERWORLD)));
        }
    }

}