package com.izofar.takesapillage.client.renderer.layer;

import com.google.common.collect.ImmutableMap;
import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.client.model.ClayGolemModel;
import com.izofar.takesapillage.entity.ClayGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ClayGolemCrackinessLayer extends RenderLayer<ClayGolem, ClayGolemModel> {

    private static final Map<IronGolem.Crackiness, ResourceLocation> resourceLocations = ImmutableMap.of(IronGolem.Crackiness.LOW, new ResourceLocation(TakesAPillageMod.MODID, "textures/entity/clay_golem/clay_golem_crackiness_low.png"), IronGolem.Crackiness.MEDIUM, new ResourceLocation(TakesAPillageMod.MODID, "textures/entity/clay_golem/clay_golem_crackiness_medium.png"), IronGolem.Crackiness.HIGH, new ResourceLocation(TakesAPillageMod.MODID, "textures/entity/clay_golem/clay_golem_crackiness_high.png"));

    public ClayGolemCrackinessLayer(RenderLayerParent<ClayGolem, ClayGolemModel> layer) { super(layer); }

    public void render(PoseStack stack, MultiBufferSource buffersource, int i, ClayGolem entity, float f0, float f1, float f2, float f3, float f4, float f5) {
        if (!entity.isInvisible()) {
            IronGolem.Crackiness claygolem$crackiness = entity.getCrackiness();
            if (claygolem$crackiness != IronGolem.Crackiness.NONE) {
                ResourceLocation resourcelocation = resourceLocations.get(claygolem$crackiness);
                renderColoredCutoutModel(this.getParentModel(), resourcelocation, stack, buffersource, i, entity, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}
