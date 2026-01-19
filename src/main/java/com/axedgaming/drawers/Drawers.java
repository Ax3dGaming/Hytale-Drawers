package com.axedgaming.drawers;

import com.axedgaming.drawers.api.PacketListener;
import com.axedgaming.drawers.interaction.PlaceDrawerInteraction;
import com.axedgaming.drawers.listener.DrawerInteractListener;
import com.hypixel.hytale.event.EventRegistration;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.HytaleServerConfig;
import com.hypixel.hytale.server.core.event.events.player.PlayerInteractEvent;
import com.hypixel.hytale.server.core.io.adapter.PacketWatcher;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import java.util.function.Consumer;

@SuppressWarnings({
        "rawtypes",
        "unchecked",
        "deprecation"
})
public class Drawers extends JavaPlugin {
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public Drawers(JavaPluginInit init){
        super(init);
        LOGGER.atInfo().log("Hello from %s version %s", this.getName(), this.getManifest().getVersion().toString());
    }

    @Override
    protected void setup() {
        PacketWatcher watcher = new PacketListener();

        LOGGER.atInfo().log("PacketListener registered");

        //Useless but we never know
        this.getCommandRegistry().registerCommand(new GetItemInMainHandCommand());
        //this.getCommandRegistry().registerCommand(new ExampleCommand(this.getName(), this.getManifest().getVersion().toString()));

        this.getCodecRegistry(Interaction.CODEC).register(
                "place_drawer",
                PlaceDrawerInteraction.class,
                PlaceDrawerInteraction.CODEC
        );

        Drawers.LOGGER.atInfo().log("place_drawer interaction registered");
    }

    private EventRegistration registration;

    public void onEnable() {

        DrawerInteractListener listener = new DrawerInteractListener();

        Consumer consumer = (event) -> {
            listener.onInteract((PlayerInteractEvent) event);
        };

        registration = HytaleServer.get()
                .getEventBus()
                .register(
                        (Class) PlayerInteractEvent.class,
                        consumer
                );

        System.out.println("[Drawers] Enabled");
    }

    public void onDisable() {

        if (registration != null) {
            registration.unregister();
        }

        System.out.println("[Drawers] Disabled");
    }
}
