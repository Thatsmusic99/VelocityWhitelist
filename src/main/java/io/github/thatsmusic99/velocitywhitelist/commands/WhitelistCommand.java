package io.github.thatsmusic99.velocitywhitelist.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github.thatsmusic99.velocitywhitelist.config.WhitelistConfig;
import io.github.thatsmusic99.velocitywhitelist.config.WhitelistList;

public class WhitelistCommand {

    public static BrigadierCommand createCommand(final ProxyServer server) {
        LiteralCommandNode<CommandSource> whitelist = LiteralArgumentBuilder
                .<CommandSource>literal("whitelist")
                .requires(source -> source.hasPermission("vwhitelist.command"))
                .then(LiteralArgumentBuilder
                        .<CommandSource>literal("add")
                        .requires(source -> source.hasPermission("vwhitelist.command.add"))
                        .then(RequiredArgumentBuilder.<CommandSource, String>argument("player", StringArgumentType.word())
                                .executes(context -> {

                                    String player = context.getArgument("player", String.class);

                                    WhitelistList.get().WHITELISTED_NAMES.add(player);
                                    try {
                                        WhitelistList.get().save();
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }

                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                ).then(LiteralArgumentBuilder
                        .<CommandSource>literal("remove")
                        .requires(source -> source.hasPermission("vwhitelist.command.remove"))
                        .then(RequiredArgumentBuilder.<CommandSource, String>argument("player", StringArgumentType.word())
                                .executes(context -> {

                                    String player = context.getArgument("player", String.class);
                                    WhitelistList.get().WHITELISTED_NAMES.remove(player);
                                    try {
                                        WhitelistList.get().save();
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }

                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                ).then(LiteralArgumentBuilder
                        .<CommandSource>literal("list")
                        .requires(source -> source.hasPermission("vwhitelist.command.list"))
                        .then(RequiredArgumentBuilder.<CommandSource, Integer>argument("page", IntegerArgumentType.integer())
                                .executes(context -> {

                                    int page = context.getArgument("page", Integer.class);
                                    return listWhitelist(context, page) ? Command.SINGLE_SUCCESS : -1;
                                })
                        )
                        .executes(context -> listWhitelist(context, 1) ? Command.SINGLE_SUCCESS : -1)
                ).then(LiteralArgumentBuilder
                        .<CommandSource>literal("reload")
                        .requires(source -> source.hasPermission("vwhitelist.command.reload"))
                        .executes(context -> {

                            try {
                                WhitelistConfig.get().reload();
                                WhitelistList.get().reload();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                            return Command.SINGLE_SUCCESS;
                        })
                ).then(LiteralArgumentBuilder
                        .<CommandSource>literal("on")
                        .requires(source -> source.hasPermission("vwhitelist.command.on"))
                        .executes(context -> {

                            WhitelistConfig.get().WHITELIST_ENABLED = true;
                            try {
                                WhitelistConfig.get().save();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                            return Command.SINGLE_SUCCESS;
                        })
                ).then(LiteralArgumentBuilder
                        .<CommandSource>literal("off")
                        .requires(source -> source.hasPermission("vwhitelist.command.off"))
                        .executes(context -> {

                            WhitelistConfig.get().WHITELIST_ENABLED = false;
                            try {
                                WhitelistConfig.get().save();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                            return Command.SINGLE_SUCCESS;
                        })
                ).build();

        return new BrigadierCommand(whitelist);
    }

    private static boolean listWhitelist(CommandContext<CommandSource> context, int page) {

        return true;
    }
}
