package com.enderio.conduits.common.conduit.type.item;

import com.enderio.EnderIO;
import com.enderio.api.conduit.ClientConduitData;
import com.enderio.api.misc.Vector2i;
import com.enderio.base.common.lang.EIOLang;
import com.enderio.conduits.common.init.EIOConduitTypes;
import com.enderio.core.client.gui.widgets.CheckBox;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ItemClientConduitData implements ClientConduitData<ItemExtendedData> {
    @Override
    public List<AbstractWidget> createWidgets(Screen screen, Supplier<ItemExtendedData> extendedConduitData,
        UpdateExtendedData<ItemExtendedData> updateExtendedConduitData, Supplier<Direction> direction, Vector2i widgetsStart) {
        // TODO: Method of doing sync that does not require CoreNetwork in API.
        List<AbstractWidget> widgets = new ArrayList<>();

        widgets.add(new CheckBox(
            EnderIO.loc("textures/gui/round_robin.png"),
            widgetsStart.add(110, 20),
            () -> extendedConduitData.get().get(direction.get()).isRoundRobin,
            bool -> {
                updateExtendedConduitData.update(data -> {
                    var sideData = data.compute(direction.get());
                    sideData.isRoundRobin = bool;
                    return data;
                });
            }, () -> EIOLang.ROUND_ROBIN_ENABLED, () -> EIOLang.ROUND_ROBIN_DISABLED));

        widgets.add(new CheckBox(
            EnderIO.loc("textures/gui/self_feed.png"),
            widgetsStart.add(130, 20),
            () -> extendedConduitData.get().get(direction.get()).isSelfFeed,
            bool -> {
                updateExtendedConduitData.update(data -> {
                    var sideData = data.compute(direction.get());
                    sideData.isSelfFeed = bool;
                    return data;
                });
            }, () -> EIOLang.SELF_FEED_ENABLED, () -> EIOLang.SELF_FEED_DISABLED));
        return widgets;
    }

    @Override
    public ResourceLocation getTextureLocation() {
        return EIOConduitTypes.ICON_TEXTURE;
    }

    @Override
    public Vector2i getTexturePosition() {
        return new Vector2i(0, 96);
    }
}
