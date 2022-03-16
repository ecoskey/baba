package net.emersoncoskey.foam.datagen.model

import cats.data.State
import cats.implicits.catsSyntaxFlatMapOps
import net.minecraft.client.renderer.block.model.BlockModel.GuiLight
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.model.generators.{ModelBuilder, ModelFile, ModelProvider}
import net.minecraftforge.common.data.ExistingFileHelper

case class CbModel[A <: ModelBuilder[A]](name: String, act: State[A, _])

object CbModel {
	val BlockFolder: String = ModelProvider.BLOCK_FOLDER
	val ItemFolder : String = ModelProvider.ITEM_FOLDER

	def parent[A <: ModelBuilder[A]](parent: String): State[A, Unit] = this.parent(new ResourceLocation(parent))

	def parent[A <: ModelBuilder[A]](parent: ResourceLocation): State[A, Unit] = this.parent(getExistingModel(parent))

	def parent[A <: ModelBuilder[A]](parent: ModelFile): State[A, Unit] = State.modify(_.parent(parent))

	def texture[A <: ModelBuilder[A]](key: String, texture: String): State[A, Unit] =
		State.modify(_.texture(key, texture))

	def texture[A <: ModelBuilder[A]](key: String, texture: ResourceLocation): State[A, Unit] =
		State.modify(_.texture(key, texture))

	def ao[A <: ModelBuilder[A]](ao: Boolean): State[A, Unit] = State.modify(_.ao(ao))

	def guiLight[A <: ModelBuilder[A]](light: GuiLight): State[A, Unit] = State.modify(_.guiLight(light))

	/* [UTILITY METHODS] **********************************************************************************************/

	def getExistingModel(
		path: ResourceLocation,
		folder: String,
		helper: ExistingFileHelper
	): ModelFile.ExistingModelFile =
		new ModelFile.ExistingModelFile(
			new ResourceLocation(path.getNamespace, s"$folder/${path.getPath}"),
			helper
		)

	/* ["SHORTCUT" METHODS] *******************************************************************************************/

	def cube[A <: ModelBuilder[A]](
		name : String,
		down : ResourceLocation,
		up   : ResourceLocation,
		north: ResourceLocation,
		south: ResourceLocation,
		east : ResourceLocation,
		west : ResourceLocation
	): CbModel[A] = CbModel(
		name, for {
			_ <- parent(getExistingModel(new ResourceLocation("cube")))
			_ <- texture("down", down)
			_ <- texture("up", up)
			_ <- texture("north", north)
			_ <- texture("south", south)
			_ <- texture("east", east)
			_ <- texture("west", west)
		} yield ()
	)

	def singleTexture[A <: ModelBuilder[A]](
		name   : String,
		parent : String,
		texture: ResourceLocation
	): CbModel[A] = singleTexture(name, new ResourceLocation(parent), texture)

	def singleTexture[A <: ModelBuilder[A]](
		name   : String,
		parent : ResourceLocation,
		texture: ResourceLocation
	): CbModel[A] = singleTexture(name, parent, "texture", texture)

	def singleTexture[A <: ModelBuilder[A]](
		name      : String,
		parent    : String,
		textureKey: String,
		texture   : ResourceLocation,
	): CbModel[A] = singleTexture(name, new ResourceLocation(parent), textureKey, texture)

	def singleTexture[A <: ModelBuilder[A]](
		name      : String,
		parent    : ResourceLocation,
		textureKey: String,
		texture   : ResourceLocation
	): CbModel[A] = CbModel(name, this.parent(parent) >> this.texture(textureKey, texture))

	def cubeAll[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/cube_all", "all", texture)

	def cubeTop[A <: ModelBuilder[A]](name: String, side: ResourceLocation, top: ResourceLocation): CbModel[A] =
		CbModel(name, for {
			_ <- parent(getExistingModel(new ResourceLocation(s"$BlockFolder/cube_top")))
			_ <- texture("side", side)
			_ <- texture("top", top)
		} yield ())

	def sideBottomTop[A <: ModelBuilder[A]](
		name  : String,
		parent: String,
		side  : ResourceLocation,
		bottom: ResourceLocation,
		top   : ResourceLocation
	): CbModel[A] = CbModel(name, for {
		_ <- this.parent(parent)
		_ <- texture("side", side)
		_ <- texture("bottom", bottom)
		_ <- texture("top", top)
	} yield ())

	def cubeBottomTop[A <: ModelBuilder[A]](
		name  : String,
		side  : ResourceLocation,
		bottom: ResourceLocation,
		top   : ResourceLocation
	): CbModel[A] = CbModel(name, for {
		_ <- parent(s"$BlockFolder/cube_bottom_top")
		_ <- texture("side", side)
		_ <- texture("bottom", bottom)
		_ <- texture("top", top)
	} yield ())

	def cubeBottomTop[A <: ModelBuilder[A]](
		name: String,
		side: ResourceLocation,
		end : ResourceLocation,
	): CbModel[A] = CbModel(name, for {
		_ <- parent(s"$BlockFolder/cube_column")
		_ <- texture("side", side)
		_ <- texture("end", end)
	} yield ())

	def cubeColumnHorizontal[A <: ModelBuilder[A]](
		name: String,
		side: ResourceLocation,
		end : ResourceLocation,
	): CbModel[A] = CbModel(name, for {
		_ <- parent(s"$BlockFolder/cube_column_horizontal")
		_ <- texture("side", side)
		_ <- texture("end", end)
	} yield ())

	def cubeOrientableVertical[A <: ModelBuilder[A]](
		name : String,
		side : ResourceLocation,
		front: ResourceLocation,
	): CbModel[A] = CbModel(name, for {
		_ <- parent(s"$BlockFolder/cube_column")
		_ <- texture("side", side)
		_ <- texture("front", front)
	} yield ())

	def orientableWithBottom[A <: ModelBuilder[A]](
		name  : String,
		side  : ResourceLocation,
		front : ResourceLocation,
		bottom: ResourceLocation,
		top   : ResourceLocation,
	): CbModel[A] = CbModel(name, for {
		_ <- parent(s"$BlockFolder/cube_column")
		_ <- texture("side", side)
		_ <- texture("front", front)
		_ <- texture("bottom", bottom)
		_ <- texture("top", top)
	} yield ())

	def orientable[A <: ModelBuilder[A]](
		name : String,
		side : ResourceLocation,
		front: ResourceLocation,
		top  : ResourceLocation,
	): CbModel[A] = CbModel(name, for {
		_ <- parent(s"$BlockFolder/cube_column")
		_ <- texture("side", side)
		_ <- texture("front", front)
		_ <- texture("top", top)
	} yield ())

	def crop[A <: ModelBuilder[A]](name: String, crop: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/crop", "crop", crop)

	def cross[A <: ModelBuilder[A]](name: String, cross: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/cross", "cross", cross)

	def stairs[A <: ModelBuilder[A]](
		name  : String,
		side  : ResourceLocation,
		bottom: ResourceLocation,
		top   : ResourceLocation
	): CbModel[A] = sideBottomTop(name, s"$BlockFolder/stairs", side, bottom, top)

	//todo: ADD THE REST OF THESE METHODS FROM MODELPROVIDER

	def stairsOuter[A <: ModelBuilder[A]](
		name: String,
		side: ResourceLocation,
		bottom: ResourceLocation,
		top: ResourceLocation
	): CbModel[A] = sideBottomTop(name, s"$BlockFolder/outer_stairs", side, bottom, top)

	def stairsInner[A <: ModelBuilder[A]](
		name: String,
		side: ResourceLocation,
		bottom: ResourceLocation,
		top: ResourceLocation
	): CbModel[A] = sideBottomTop(name, s"$BlockFolder/inner_stairs", side, bottom, top)

	def slab[A <: ModelBuilder[A]](
		name: String,
		side: ResourceLocation,
		bottom: ResourceLocation,
		top: ResourceLocation
	): CbModel[A] = sideBottomTop(name, s"$BlockFolder/slab", side, bottom, top)

	def slabTop[A <: ModelBuilder[A]](
		name: String,
		side: ResourceLocation,
		bottom: ResourceLocation,
		top: ResourceLocation
	): CbModel[A] = sideBottomTop(name, s"$BlockFolder/slab_top", side, bottom, top)

	def button[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/button", texture)

	def buttonPressed[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/button_pressed", texture)

	def buttonInventory[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/button_inventory", texture)

	def pressurePlate[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/pressure_plate_up", texture)

	def pressurePlateDown[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/pressure_plate_down", texture)

	def sign[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		CbModel(name, this.texture("particle", texture))

	def fencePost[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/fence_post", texture)

	def fenceSide[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/fence_side", texture)

	def fenceInventory[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/fence_inventory", texture)

	def fenceGate[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/template_fence_gate", texture)

	def fenceGateOpen[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/template_fence_gate_open", texture)

	def fenceGateWall[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/template_fence_gate_wall", texture)

	def fenceGateWallOpen[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/template_fence_gate_wall_open", texture)

	def wallPost[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/template_wall_post", "wall", texture)

	def wallSide[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/template_wall_side", "wall", texture)

	def wallSideTall[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/template_wall_side_tall", "wall", texture)

	def wallInventory[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/wall_inventory", "wall", texture)

	private def pane[A <: ModelBuilder[A]](
		name: String,
		parent: String,
		pane: ResourceLocation,
		edge: ResourceLocation
	): CbModel[A] = CbModel(name, for {
		_ <- this.parent(s"$BlockFolder/$parent")
		_ <- texture("pane", pane)
		_ <- texture("edge", edge)
	} yield ())

	def panePost[A <: ModelBuilder[A]](name: String, pane: ResourceLocation, edge: ResourceLocation): CbModel[A] =
		this.pane(name, "template_glass_pane_post", pane, edge)

	def paneSide[A <: ModelBuilder[A]](name: String, pane: ResourceLocation, edge: ResourceLocation): CbModel[A] =
		this.pane(name, "template_glass_pane_side", pane, edge)

	def paneSideAlt[A <: ModelBuilder[A]](name: String, pane: ResourceLocation, edge: ResourceLocation): CbModel[A] =
		this.pane(name, "template_glass_pane_side_alt", pane, edge)

	def paneNoSide[A <: ModelBuilder[A]](name: String, pane: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/template_glass_pane_noside", "pane", pane)

	def paneNoSideAlt[A <: ModelBuilder[A]](name: String, pane: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/template_glass_pane_noside_alt", "pane", pane)

	private def door[A <: ModelBuilder[A]](
		name: String,
		model: String,
		bottom: ResourceLocation,
		top: ResourceLocation
	): CbModel[A] = CbModel(name, for {
		_ <- parent(s"$BlockFolder/$model")
		_ <- texture("bottom", bottom)
		_ <- texture("top", top)
	} yield ())

	def doorBottomLeft[A <: ModelBuilder[A]](
		name: String,
		bottom: ResourceLocation,
		top: ResourceLocation
	): CbModel[A] = door(name, "door_bottom", bottom, top)

	def doorBottomRight[A <: ModelBuilder[A]](
		name: String,
		bottom: ResourceLocation,
		top: ResourceLocation
	): CbModel[A] = door(name, "door_bottom_rh", bottom, top)

	def doorTopLeft[A <: ModelBuilder[A]](
		name: String,
		bottom: ResourceLocation,
		top: ResourceLocation
	): CbModel[A] = door(name, "door_top", bottom, top)

	def doorTopRight[A <: ModelBuilder[A]](
		name: String,
		bottom: ResourceLocation,
		top: ResourceLocation
	): CbModel[A] = door(name, "door_top_rh", bottom, top)

	def trapdoorBottom[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/template_trapdoor_bottom", texture)

	def trapdoorTop[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/template_trapdoor_top", texture)

	def trapdoorOpen[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/template_trapdoor_open", texture)

	def trapdoorOrientableBottom[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/template_orientable_trapdoor_bottom", texture)

	def trapdoorOrientableTop[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/template_orientable_trapdoor_top", texture)

	def trapdoorOrientableOpen[A <: ModelBuilder[A]](name: String, texture: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/template_orientable_trapdoor_open", texture)

	def torch[A <: ModelBuilder[A]](name: String, torch: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/template_torch", "torch", torch)

	def torchWall[A <: ModelBuilder[A]](name: String, torch: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/template_torch_wall", "torch", torch)

	def carpet[A <: ModelBuilder[A]](name: String, wool: ResourceLocation): CbModel[A] =
		singleTexture(name, s"$BlockFolder/carpet", "wool", wool)
}
