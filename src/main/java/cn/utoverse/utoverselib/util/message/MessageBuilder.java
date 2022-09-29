package cn.utoverse.utoverselib.util.message;

import cn.utoverse.utoverselib.util.Util;
import cn.utoverse.utoverselib.util.locale.ItemLocale;
import de.themoep.minedown.MineDown;
import de.tr7zw.nbtapi.NBTItem;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

/**
 * 消息构造器
 * 使用链式表达式，简化 Minedown 的使用成本
 * Minedown docs: https://github.com/Phoenix616/MineDown
 */
public class MessageBuilder {

    private ComponentBuilder componentBuilder = new ComponentBuilder();
    private MessageLevel messageLevel = MessageLevel.NONE;

    /**
     * 追加文字
     *
     * @param str 文字
     * @return {@code this}.
     */
    public MessageBuilder append(String str) {
        append(str, true, ComponentBuilder.FormatRetention.NONE);
        return this;
    }

    /**
     * 追加文字
     *
     * @param str             文字
     * @param isMineDown      是否为 Minedown 语法
     * @param formatRetention 格式保留。简而言之，是否继承上一个文字组件的样式行为。
     * @return {@code this}.
     */
    public MessageBuilder append(String str, boolean isMineDown, ComponentBuilder.FormatRetention formatRetention) {
        if (isMineDown) {
            componentBuilder.append(MineDown.parse(messageLevel.getChatColor() + str), formatRetention);
        } else {
            componentBuilder.append(messageLevel.getChatColor() + str, formatRetention);
        }
        return this;
    }

    /**
     * 添加一个生物实体
     *
     * @param entity 生物实体
     * @param <T>    继承自Entity的实体
     * @return {@code this}.
     */
    public <T extends Entity> MessageBuilder appendEntity(T entity) {
        String entityStr = "[&#FC8BAB&[{name}&#FC8BAB&]&r](show_entity={uuid}:{type} {name})"
                .replace("{name}", Objects.requireNonNullElse(entity.getCustomName(), entity.getName()))
                .replace("{uuid}", entity.getUniqueId().toString())
                .replace("{type}", entity.getType().name().toLowerCase());
        componentBuilder.append(MineDown.parse(entityStr), ComponentBuilder.FormatRetention.NONE);
        return this;
    }

    /**
     * 添加可悬浮字
     *
     * @param label 显示文字
     * @param text  悬浮的文字
     * @return {@code this}.
     */
    public MessageBuilder appendHover(String label, String text) {
        String minedownStr = "[&r{label}](show_text={text})".replace("{label}", label).replace("{text}", text);
        append(minedownStr, true, ComponentBuilder.FormatRetention.NONE);
        return this;
    }

    /**
     * 添加点击式执行指令
     *
     * @param command 指令
     * @return {@code this}.
     */
    public MessageBuilder appendCommand(String label, String command) {
        return appendCommand(label, command, "点击执行此指令");
    }

    /**
     * 添加点击式执行指令
     *
     * @param command   指令
     * @param hoverText 悬浮在指令上的文字
     * @return {@code this}.
     */
    public MessageBuilder appendCommand(String label, String command, String hoverText) {
        String cmd = command.startsWith("/") ? command : "/" + command;

        String minedownStr = "[&#FFA500&&r{label}&r](show_text={text} run_command={cmd})".replace("{label}", label).replace("{text}", hoverText).replace("{cmd}", cmd);
        append(minedownStr, true, ComponentBuilder.FormatRetention.NONE);
        return this;
    }

    /**
     * 添加一个物品堆 (ItemStack)
     *
     * @param itemStack 物品堆
     * @return {@code this}.
     */
    public MessageBuilder appendItemStack(ItemStack itemStack) {
        int amount = itemStack.getAmount();
        String namespaceKey = itemStack.getType().toString();

        String displayName = ItemLocale.getI18n(namespaceKey);
        ItemMeta imeta = itemStack.getItemMeta();
        if (imeta != null) {
            String nameFromMeta = itemStack.getItemMeta().getDisplayName();
            if (!StringUtils.isEmpty(nameFromMeta)) {
                displayName = nameFromMeta;
            }
        }

        TextComponent tooltipComp = getItemTooltipComponent(String.format("&b[%s&b]&r", displayName), itemStack, namespaceKey.toLowerCase(), amount);
        componentBuilder.append(tooltipComp);
        return this;
    }

    /**
     * 添加一个空格。主要用于间距。
     *
     * @return {@code this}.
     */
    public MessageBuilder space() {
        append(ChatColor.RESET + " ", true, ComponentBuilder.FormatRetention.NONE);
        return this;
    }

    /**
     * 添加一个回车。主要用于间距。
     *
     * @return {@code this}.
     */
    public MessageBuilder enter() {
        append(ChatColor.RESET + "\n", true, ComponentBuilder.FormatRetention.NONE);
        return this;
    }

    /**
     * 普通信息颜色
     *
     * @return {@code this}.
     */
    public MessageBuilder info() {
        messageLevel = MessageLevel.INFO;
        return this;
    }

    /**
     * 警告信息颜色
     *
     * @return {@code this}.
     */
    public MessageBuilder warn() {
        messageLevel = MessageLevel.WARN;
        return this;
    }

    /**
     * 构建 消息构造器
     *
     * @return BaseComponent[]
     */
    public BaseComponent[] build() {
        return this.componentBuilder.create();
    }

    private static TextComponent getItemTooltipComponent(String message, ItemStack item, String id, int count) {
        NBTItem nbti = new NBTItem(item);
        String itemJson = nbti.toString();

        // Prepare a BaseComponent array with the itemJson as a text component
        BaseComponent[] hoverEventComponents = new BaseComponent[]{
                new TextComponent(itemJson) // The only element of the hover events basecomponents is the item json
        };

        // Create the hover event
        HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_ITEM, new Item(id, count, ItemTag.ofNbt(itemJson)));

        /* And now we create the text component (this is the actual text that the player sees)
         * and set it's hover event to the item event */
        TextComponent component = new TextComponent(Util.parseColor(message));
        component.setHoverEvent(event);
        return component;
    }

}
