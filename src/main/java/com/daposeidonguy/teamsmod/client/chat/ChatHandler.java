package com.daposeidonguy.teamsmod.client.chat;

import com.mojang.datafixers.util.Pair;

import java.util.UUID;

public class ChatHandler {

    public static Pair<UUID, String> lastMessageReceived;
    public static boolean lastMessageTeam = false;

}