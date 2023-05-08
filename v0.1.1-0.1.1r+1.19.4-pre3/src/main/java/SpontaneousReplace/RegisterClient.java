package SpontaneousReplace;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * <b style="color:FFC800"><font size="+2">RegisterClient：自然更替客户端注册器</font></b>
 * <p><i><b style="color:FFC800"><font size="+1">专门集中注册模组客户端内容的类</font></b></i></p>
 * <style="color:FFC800">
 *
 * @author 刘 Saikel Orado 又称 “游戏极客-Saikel”
 * <p>Saikel Orado Liu aka ”GameGeek-Saikel“</p>
 * @version 3.0
 * | 创建于 < 2023/1/5
 */
@Environment(EnvType.CLIENT)
public abstract class RegisterClient {
    public static void register() {
        SpontaneousReplace.SpiderBiome.Client.register();
    }
}