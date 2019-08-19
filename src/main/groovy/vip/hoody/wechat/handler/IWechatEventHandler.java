package vip.hoody.wechat.handler;

import vip.hoody.wechat.domain.event.*;
import vip.hoody.wechat.domain.event.menu.*;
import vip.hoody.wechat.domain.reply.ReplyBaseMsg;

/**
 * 微信事件处理器接口
 */
public interface IWechatEventHandler {

    ReplyBaseMsg handle(LocationEvent event);

    /**
     * 点击推事件用户点击click类型按钮后，
     * 微信服务器会通过消息接口推送消息类型为event的结构给开发者（参考消息接口指南），
     * 并且带上按钮中开发者填写的key值，
     * 开发者可以通过自定义的key值与用户进行交互
     *
     * @param event
     * @return ReplyBaseMsg 可以返回消息给用户
     */
    ReplyBaseMsg handle(ClickEvent event);

    /**
     * 跳转URL用户点击view类型按钮后，
     * 微信客户端将会打开开发者在按钮中填写的网页URL，
     * 可与网页授权获取用户基本信息接口结合，获得用户基本信息
     *
     * @param event
     * @return null 无消息返回给用户
     */
    ReplyBaseMsg handle(ViewEvent event);

    ReplyBaseMsg handle(ScanQREvent event);

    ReplyBaseMsg handle(ScanQRUnSubEvent event);

    /**
     * 用户关注事件
     *
     * @param event
     * @return ReplyBaseMsg 会作为关注后第一条消息发送给用户
     */
    ReplyBaseMsg handle(SubscribeEvent event);

    /**
     * 扫码推事件用户点击按钮后，微信客户端将调起扫一扫工具，
     * 完成扫码操作后显示扫描结果（如果是URL，将进入URL），
     * 且会将扫码的结果传给开发者，开发者可以下发消息
     *
     * @param event
     * @return null 无返回值到用户
     */
    ReplyBaseMsg handle(ScanCodePushEvent event);

    /**
     * 扫码推事件且弹出“消息接收中”提示框用户点击按钮后，
     * 微信客户端将调起扫一扫工具，完成扫码操作后，
     * 将扫码的结果传给开发者，同时收起扫一扫工具，
     * 然后弹出“消息接收中”提示框，随后可能会收到开发者下发的消息
     *
     * @return ReplyBaseMsg 有返回值到用户
     */
    ReplyBaseMsg handle(ScanCodeWaitEvent event);

    /**
     * 弹出地理位置选择器用户点击按钮后，
     * 微信客户端将调起地理位置选择工具，
     * 完成选择操作后，将选择的地理位置发送给开发者的服务器，
     * 同时收起位置选择工具，随后可能会收到开发者下发的消息
     *
     * @param event
     * @return null 无返回值到用户,但是可以通过MSG handler 收到定位信息
     */
    ReplyBaseMsg handle(LocationSelectEvent event);

    /**
     * 弹出系统拍照发图用户点击按钮后，微信客户端将调起系统相机，
     * 完成拍照操作后，会将拍摄的相片发送给开发者，并推送事件给开发者，
     * 同时收起系统相机，随后可能会收到开发者下发的消息
     *
     * @param event
     * @return null 无返回值 到用户端,图片消息随后会由msg handler 处理
     */
    ReplyBaseMsg handle(PicSysPhotoEvent event);

    /**
     * pic_photo_or_album 事件
     * 弹出拍照或者相册发图用户点击按钮后，
     * 微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。
     * 用户选择后即走其他两种流程。
     *
     * @param event
     * @return null 无返回值 到用户端,图片消息随后会由msg handler 处理
     */
    ReplyBaseMsg handle(PicPhotoOrAlbumEvent event);

    /**
     * 弹出微信相册发图器用户点击按钮后，
     * 微信客户端将调起微信相册，完成选择操作后，
     * 将选择的相片发送给开发者的服务器，并推送事件给开发者，同时收起相册，
     * 随后可能会收到开发者下发的消息
     *
     * @param event
     * @return null 无返回值 到用户端,图片消息随后会由msg handler 处理
     */
    ReplyBaseMsg handle(PicWeixinEvent event);

}
