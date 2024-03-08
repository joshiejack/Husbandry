//package uk.joshiejack.husbandry.plugin;
//
//import dev.latvian.mods.kubejs.script.BindingsEvent;
//import dev.latvian.mods.kubejs.script.ScriptType;
//import dev.latvian.mods.kubejs.util.ClassFilter;
//import uk.joshiejack.husbandry.Husbandry;
//import uk.joshiejack.husbandry.plugin.kubejs.HusbandryUtils;
//
//public class KubeJSPlugin extends dev.latvian.mods.kubejs.KubeJSPlugin {
//    @Override
//    public void registerClasses(ScriptType type, ClassFilter filter) {
//        filter.allow("uk.joshiejack.husbandry.plugins.kubejs");
//    }
//
//    @Override
//    public void registerBindings(BindingsEvent event) {
//        if (event.getType() != ScriptType.STARTUP)
//            event.add(Husbandry.MODID, HusbandryUtils.class);
//    }
//}