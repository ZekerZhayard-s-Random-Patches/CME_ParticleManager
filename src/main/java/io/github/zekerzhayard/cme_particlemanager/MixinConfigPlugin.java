package io.github.zekerzhayard.cme_particlemanager;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class MixinConfigPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        MappingResolver mr = FabricLoader.getInstance().getMappingResolver();
        for (MethodNode mn : targetClass.methods) {
            if (Objects.equals(mn.name, mr.mapMethodName("intermediary", "net.minecraft.class_702", "method_18125", "(Lnet/minecraft/class_3999;)Ljava/util/Queue;")) && Objects.equals(mn.desc, "(L" + mr.mapClassName("intermediary", "net.minecraft.class_3999").replace(".", "/") + ";)Ljava/util/Queue;")) {
                for (AbstractInsnNode ain : mn.instructions.toArray()) {
                    if (ain.getOpcode() == Opcodes.ARETURN) {
                        mn.instructions.insertBefore(ain, new InsnNode(Opcodes.POP));
                        mn.instructions.insertBefore(ain, new TypeInsnNode(Opcodes.NEW, "java/util/concurrent/ConcurrentLinkedQueue"));
                        mn.instructions.insertBefore(ain, new InsnNode(Opcodes.DUP));
                        mn.instructions.insertBefore(ain, new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/util/concurrent/ConcurrentLinkedQueue", "<init>", "()V", false));
                    }
                }
            }
        }
    }
}
