package org.example.controller;

import org.example.core.AjaxResult;
import org.example.request.cartoon.AddPatternsReq;
import org.example.request.CartoonSaleNumReq;
import org.example.request.base.UploadReq;
import org.example.request.cartoon.CreateCartoonReq;
import org.example.request.cartoon.UpdateCartoonReq;
import org.example.service.ICartoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cartoon")
public class CartoonController {

    @Autowired
    private ICartoonService cartoonService;

    /**
     * 获取推荐的漫画,这个要结合 order 服务来实现
     * order 当用户下单的时候，将漫画和用户绑定，
     * 推荐系统根据用户平时 观看 or 购买的漫画进行推荐
     * @return 根据用户喜欢的漫画推荐
     */
    @GetMapping("/recommend")
    public AjaxResult recommend() {
        return AjaxResult.error();
    }

    /**
     * 根据tag获取漫画列表
     * @param tag 标签
     * @return
     */
    @GetMapping("/cartoonListByTag/{tag}")
    public AjaxResult cartoonListByTag(
            @PathVariable("tag") String tag
    ) {
        return cartoonService.cartoonListByTag(tag);
    }

    /**
     * 获取漫画单集的价格
     * @return 根据用户喜欢的漫画推荐
     */
    @GetMapping("/price/{cartoonId}")
    public AjaxResult price(
            @PathVariable("cartoonId") String cartoonId
    ) {
        return cartoonService.price(cartoonId);
    }

    /**
     * 当漫画销售之后，更新漫画的销售额
     * @return
     */
    @PostMapping("/sales")
    public AjaxResult sales(
            @RequestBody CartoonSaleNumReq req
            ) {
        return cartoonService.sales(req);
    }

    /**
     * 获取漫画列表 一次获取10个漫画
     * @return 漫画列表
     */
    @GetMapping("/list/{order}/{page}/{size}")
    public AjaxResult salesList(
            @PathVariable("order") String order,
            @PathVariable("page") int page,
            @PathVariable("size") int size
    ) {
        return cartoonService.list(order,page,size);
    }

    /**
     * 获取我创建的漫画列表
     * @return 漫画列表
     */
    @GetMapping("/meCreate")
    public AjaxResult meCreate() {
        return cartoonService.meCreate();
    }

    /**
     * 获取我参加的漫画列表
     * @return 漫画列表
     */
    @GetMapping("/meJoin")
    public AjaxResult meJoin() {
        return cartoonService.meJoin();
    }

    /**
     * 根据漫画id查看该漫画的信息
     * 以及章节列表
     * @param cartoonId 漫画id
     * @return CartoonInfo
     */
    @GetMapping("/cartoonInfo/{cartoonId}")
    public AjaxResult cartoonInfo(
            @PathVariable("cartoonId") String cartoonId
    ){
        return cartoonService.cartoonInfo(cartoonId);
    }

    /**
     * 根据漫画 id 获取漫画下的所有章节，包含 DOING 状态的
     * @param cartoonId 漫画id
     * @return CartoonInfo
     */
    @GetMapping("/chapterList/{cartoonId}")
    public AjaxResult chapterList(
            @PathVariable("cartoonId") String cartoonId
    ){
        return cartoonService.chapterList(cartoonId);
    }

    /**
     * 创建漫画
     * @param req 创建漫画请求结构体
     * @return AjaxResult
     */
    @PostMapping("/createCartoon")
    public AjaxResult createCartoon(
            @RequestBody CreateCartoonReq req
    ){

        return cartoonService.createCartoon(req);
    }


    /**
     * 上传漫画的封面
     * @return AjaxResult
     */
    @PostMapping("/uploadCoverImg")
    public AjaxResult uploadCoverImg(
            @RequestBody UploadReq req
            ){
        Assert.notNull(req.getUrl(),"链接不能为空");
        Assert.notNull(req.getId(),"漫画id不能为空");
        return cartoonService.uploadCoverImg(req.getUrl(),req.getId());
    }

    /**
     * 更新漫画信息
     * @param req 漫画信息
     * @return AjaxResult
     */
    @PostMapping("/updateCartoonInfo")
    public AjaxResult updateCartoonInfo(
            @RequestBody UpdateCartoonReq req
    ){
        return cartoonService.updateCartoonInfo(req);
    }


    /**
     * 添加一个漫画的参与者
     * @param req 包含漫画id和用户id
     * @return AjaxResult
     */
    @PostMapping("/addPattern")
    public AjaxResult addPattern(
            @RequestBody AddPatternsReq req
    ){
        return cartoonService.addPattern(req);
    }

    /**
     * 获取漫画参与者
     */
    @GetMapping("/cartoonPatternList/{cartoonId}")
    public AjaxResult cartoonPatternList(
            @PathVariable("cartoonId") String cartoonId
    ){
        return cartoonService.cartoonPatternList(cartoonId);
    }

}
