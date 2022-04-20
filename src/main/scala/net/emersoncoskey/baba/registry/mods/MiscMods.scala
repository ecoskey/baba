package net.emersoncoskey.baba.registry.mods

trait MiscMods {
	/*def tags[A](first: TagKey[A], rest: TagKey[A]*)(target: A): EventHandler.Mod =
		EventHandler.Mod[GatherDataEvent](classOf, (event, mod) => {
			val gen    = event.getGenerator
			val helper = event.getExistingFileHelper
			gen.addProvider(new BabaItemTagsProvider(mod, gen, helper, TagAssignment(target, first :: rest.toList) :: Nil))
		})*/
}
