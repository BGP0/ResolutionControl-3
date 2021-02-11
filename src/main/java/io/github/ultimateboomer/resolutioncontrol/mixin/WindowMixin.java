package io.github.ultimateboomer.resolutioncontrol.mixin;

import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import io.github.ultimateboomer.resolutioncontrol.ResolutionControlMod;

@Mixin(Window.class)
public abstract class WindowMixin {
	@Inject(at = @At("RETURN"), method = "getFramebufferWidth", cancellable = true)
	private void getFramebufferWidth(CallbackInfoReturnable<Integer> callbackInfo) {
		callbackInfo.setReturnValue(scale(callbackInfo.getReturnValueI()));
	}
	
	@Inject(at = @At("RETURN"), method = "getFramebufferHeight", cancellable = true)
	private void getFramebufferHeight(CallbackInfoReturnable<Integer> callbackInfo) {
		callbackInfo.setReturnValue(scale(callbackInfo.getReturnValueI()));
	}
	
	private int scale(int value) {
		double scaleFactor = ResolutionControlMod.getInstance().getCurrentScaleFactor();
		return (int) Math.ceil(1d * value * scaleFactor);
	}
	
	@Inject(at = @At("RETURN"), method = "getScaleFactor", cancellable = true)
	private void getScaleFactor(CallbackInfoReturnable<Double> callbackInfo) {
		callbackInfo.setReturnValue(callbackInfo.getReturnValueD() / ResolutionControlMod.getInstance().getCurrentScaleFactor());
	}
	
	@Inject(at = @At("RETURN"), method = "onFramebufferSizeChanged")
	private void onFramebufferSizeChanged(CallbackInfo callbackInfo) {
		ResolutionControlMod.getInstance().onResolutionChanged();
	}
	
	@Inject(at = @At("RETURN"), method = "updateFramebufferSize")
	private void onUpdateFramebufferSize(CallbackInfo callbackInfo) {
		ResolutionControlMod.getInstance().onResolutionChanged();
	}
}
