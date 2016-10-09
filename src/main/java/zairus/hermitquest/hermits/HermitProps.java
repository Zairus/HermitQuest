package zairus.hermitquest.hermits;

import net.minecraft.util.ResourceLocation;
import zairus.hermitquest.HQConstants;

public class HermitProps
{
	public static final ResourceLocation BANNER_TEXTURE_XISUMA = new ResourceLocation(HQConstants.MODID, getHermitBannerTexture(Hermits.XISUMA));
	public static final ResourceLocation BANNER_TEXTURE_ISKALL85 = new ResourceLocation(HQConstants.MODID, getHermitBannerTexture(Hermits.ISKALL85));
	public static final ResourceLocation BANNER_TEXTURE_WELSKNIGHT = new ResourceLocation(HQConstants.MODID, getHermitBannerTexture(Hermits.WELSKNIGHT));
	public static final ResourceLocation BANNER_TEXTURE_RENDOG = new ResourceLocation(HQConstants.MODID, getHermitBannerTexture(Hermits.RENDOG));
	public static final ResourceLocation BANNER_TEXTURE_FALSESYMMETRY = new ResourceLocation(HQConstants.MODID, getHermitBannerTexture(Hermits.FALSESYMMETRY));
	public static final ResourceLocation BANNER_TEXTURE_BIFFA2001 = new ResourceLocation(HQConstants.MODID, getHermitBannerTexture(Hermits.BIFFA2001));
	public static final ResourceLocation BANNER_TEXTURE_IJEVIN = new ResourceLocation(HQConstants.MODID, getHermitBannerTexture(Hermits.IJEVIN));
	public static final ResourceLocation BANNER_TEXTURE_ZOMBIECLEO = new ResourceLocation(HQConstants.MODID, getHermitBannerTexture(Hermits.ZOMBIECLEO));
	public static final ResourceLocation BANNER_TEXTURE_KINGDADDYDMAC = new ResourceLocation(HQConstants.MODID, getHermitBannerTexture(Hermits.KINGDADDYDMAC));
	public static final ResourceLocation BANNER_TEXTURE_PYTHONGB = new ResourceLocation(HQConstants.MODID, getHermitBannerTexture(Hermits.PYTHONGB));
	public static final ResourceLocation BANNER_TEXTURE_JOEHILLS = new ResourceLocation(HQConstants.MODID, getHermitBannerTexture(Hermits.JOEHILLS));
	public static final ResourceLocation BANNER_TEXTURE_ZUELJIN = new ResourceLocation(HQConstants.MODID, getHermitBannerTexture(Hermits.ZUELJIN));
	public static final ResourceLocation BANNER_TEXTURE_GOODTIMESWITHSCAR = new ResourceLocation(HQConstants.MODID, getHermitBannerTexture(Hermits.GOODTIMESWITHSCAR));
	public static final ResourceLocation BANNER_TEXTURE_CUBFAN135 = new ResourceLocation(HQConstants.MODID, getHermitBannerTexture(Hermits.CUBFAN135));
	public static final ResourceLocation BANNER_TEXTURE_XBCRAFTED = new ResourceLocation(HQConstants.MODID, getHermitBannerTexture(Hermits.XBCRAFTED));
	public static final ResourceLocation BANNER_TEXTURE_HYPNO = new ResourceLocation(HQConstants.MODID, getHermitBannerTexture(Hermits.HYPNO));
	public static final ResourceLocation BANNER_TEXTURE_JESSASSIN = new ResourceLocation(HQConstants.MODID, getHermitBannerTexture(Hermits.JESSASSIN));
	
	private static final String getHermitBannerTexture(Hermits hermit)
	{
		return "textures/entity/hermit_banners/banner_" + hermit.NAME + ".png";
	}
	
	public static ResourceLocation[] getBannerTexturesArray()
	{
		return new ResourceLocation[] {
				BANNER_TEXTURE_XISUMA,
				BANNER_TEXTURE_ISKALL85,
				BANNER_TEXTURE_WELSKNIGHT,
				BANNER_TEXTURE_RENDOG,
				BANNER_TEXTURE_FALSESYMMETRY,
				BANNER_TEXTURE_BIFFA2001,
				BANNER_TEXTURE_IJEVIN,
				BANNER_TEXTURE_ZOMBIECLEO,
				BANNER_TEXTURE_KINGDADDYDMAC,
				BANNER_TEXTURE_PYTHONGB,
				BANNER_TEXTURE_JOEHILLS,
				BANNER_TEXTURE_ZUELJIN,
				BANNER_TEXTURE_GOODTIMESWITHSCAR,
				BANNER_TEXTURE_CUBFAN135,
				BANNER_TEXTURE_XBCRAFTED,
				BANNER_TEXTURE_HYPNO,
				BANNER_TEXTURE_JESSASSIN,
				
				BANNER_TEXTURE_XISUMA,
				BANNER_TEXTURE_XISUMA};
	}
}
