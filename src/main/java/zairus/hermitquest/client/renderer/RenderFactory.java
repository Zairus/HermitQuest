package zairus.hermitquest.client.renderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFactory<T extends Entity> implements IRenderFactory<T>
{
	public static <T extends Entity> RenderFactory<T> create(Class<? extends Render<? super T>> renderClass)
	{
		return new RenderFactory<>(renderClass);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T extends Entity> RenderFactory<T> createR2(Class<? extends Render> renderClass)
	{
		return new RenderFactory(renderClass);
	}
	
	public final Class<? extends Render<? super T>> renderClass;
	
	public RenderFactory(Class<? extends Render<? super T>> clazz)
	{
		renderClass = clazz;
	}
	
	@Override
	public Render<? super T> createRenderFor(RenderManager manager)
	{
		try
		{
			return renderClass.getConstructor(RenderManager.class).newInstance(manager);
		}
		catch (ReflectiveOperationException e)
		{
			throw new RuntimeException("Failed to construct an entity renderer of type " + renderClass + ".", e);
		}
	}
}
